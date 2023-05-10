package uz.test.abitur.services;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.CredentialsExpiredException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import uz.test.abitur.config.security.JwtUtils;
import uz.test.abitur.config.security.SessionUser;
import uz.test.abitur.domains.AuthUser;
import uz.test.abitur.domains.Document;
import uz.test.abitur.domains.UserSMS;
import uz.test.abitur.dtos.auth.*;
import uz.test.abitur.dtos.user.UserProfileUpdateDTO;
import uz.test.abitur.dtos.user.UserUpdateDTO;
import uz.test.abitur.enums.SMSCodeType;
import uz.test.abitur.enums.Status;
import uz.test.abitur.enums.TokenType;
import uz.test.abitur.repositories.AuthUserRepository;

import java.io.IOException;
import java.util.Objects;

import static uz.test.abitur.mapper.UserMapper.USER_MAPPER;

@Service
@RequiredArgsConstructor
public class AuthUserService {
    private final ApplicationEventPublisher applicationEventPublisher;
    private final JwtUtils jwtUtils;
    private final AuthenticationManager authenticationManager;
    private final AuthUserRepository authUserRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserSMSService userSMSService;
    private final DocumentService documentService;
    private final SessionUser sessionUser;


    public AuthUser create(UserCreateDTO dto) {
        if (!dto.getPassword().equals(dto.getConfirmPassword())) {
            throw new RuntimeException("Password and ConfirmPassword must be the same");
        }
        dto.setPassword(passwordEncoder.encode(dto.getPassword()));
        AuthUser user = USER_MAPPER.toEntity(dto);
        authUserRepository.save(user);
        userSMSService.createSMSCode(user, SMSCodeType.ACTIVATION);
        return user;
    }

    public TokenResponse generateToken(@NonNull TokenRequest tokenRequest) {
        String phoneNumber = tokenRequest.phoneNumber();
        String password = tokenRequest.password();
        findByPhoneNumber(phoneNumber);
        UsernamePasswordAuthenticationToken authentication =
                new UsernamePasswordAuthenticationToken(phoneNumber, password);
        authenticationManager.authenticate(authentication);
        return jwtUtils.generateToken(phoneNumber);
    }

    public TokenResponse refreshToken(@NonNull RefreshTokenRequest refreshTokenRequest) {
        String refreshToken = refreshTokenRequest.refreshToken();
        if (!jwtUtils.isTokenValid(refreshToken, TokenType.REFRESH))
            throw new CredentialsExpiredException("Token is invalid");

        String phoneNumber = jwtUtils.getUsername(refreshToken, TokenType.REFRESH);
        authUserRepository.findByPhoneNumber(phoneNumber);
        TokenResponse tokenResponse = TokenResponse.builder()
                .refreshToken(refreshToken)
                .refreshTokenExpiry(jwtUtils.getExpiry(refreshToken, TokenType.REFRESH))
                .build();
        return jwtUtils.generateAccessToken(phoneNumber, tokenResponse);
    }

    public String activate(UserActivationDTO dto) {
        String phoneNumber = dto.phoneNumber();
        String code = dto.code();
        AuthUser user = findByPhoneNumber(phoneNumber);
        UserSMS userSMS = userSMSService.findByUserId(user.getId(), SMSCodeType.ACTIVATION);
        if (!Objects.isNull(userSMS) && userSMS.getCode().equals(code)) {
            userSMS.setExpired(true);
            userSMSService.update(userSMS);
            authUserRepository.updateStatusById(Status.ACTIVE, user.getId());
            return "User activated";
        }
        throw new RuntimeException("Code is invalid");
    }

    public AuthUser update(UserUpdateDTO dto) {
        AuthUser user = findById(dto.getId());
        USER_MAPPER.updateUsersFromDTO(dto, user);
        return authUserRepository.save(user);
    }

    public boolean exist(String phoneNumber) {
        return authUserRepository.exist(phoneNumber);
    }

    public void resendCode(String phoneNumber, SMSCodeType smsCodeType) {
        AuthUser user = findByPhoneNumber(phoneNumber);
        userSMSService.createSMSCode(user, smsCodeType);
    }

    public AuthUser findByPhoneNumber(String phoneNumber) {
        return authUserRepository.findByPhoneNumber(phoneNumber)
                .orElseThrow(() -> new RuntimeException("User not found by phoneNumber: " + phoneNumber));
    }

    public AuthUser findById(String id) {
        return authUserRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    public Page<AuthUser> getAll(Status status, Pageable pageable) {
        if (status != null) {
            return authUserRepository.getAllByStatus(status, pageable);
        }
        return authUserRepository.getAll(pageable);
    }

    public void delete() {
        AuthUser user = sessionUser.user();
        user.setDeleted(true);
        authUserRepository.save(user);
    }

    public AuthUser updateProfile(UserProfileUpdateDTO dto, MultipartFile file) throws IOException {
        AuthUser user = sessionUser.user();
        USER_MAPPER.updateUsersProfileFromDTO(dto, user);
        Document document = documentService.saveMultipartDocument(file);
        user.setProfilePhotoGeneratedName(document.getGeneratedName());
        return authUserRepository.save(user);
    }

    public void resetPassword(UserResetPasswordDTO dto) {
        AuthUser authUser = findByPhoneNumber(dto.phoneNumber());
        UserSMS userSMS = userSMSService.findByUserId(authUser.getId(), SMSCodeType.FORGET_PASSWORD);
        if (!Objects.isNull(userSMS) && userSMS.getCode().equals(dto.code())) {
            userSMS.setExpired(true);
            userSMSService.update(userSMS);
            authUser.setPassword(passwordEncoder.encode(dto.password()));
            authUserRepository.save(authUser);
        }
    }
}
