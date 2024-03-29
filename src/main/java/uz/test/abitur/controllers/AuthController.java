package uz.test.abitur.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import uz.test.abitur.config.security.SessionUser;
import uz.test.abitur.domains.AuthUser;
import uz.test.abitur.dtos.ResponseDTO;
import uz.test.abitur.dtos.auth.*;
import uz.test.abitur.enums.SMSCodeType;
import uz.test.abitur.services.AuthUserService;

import static uz.test.abitur.utils.UrlUtils.BASE_AUTH_URL;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(BASE_AUTH_URL)
@Tag(name = "Authentication", description = "Authentication API")
@PreAuthorize("isAnonymous()")
public class AuthController {
    private final AuthUserService authUserService;
    private final SessionUser sessionUser;

    @Operation(summary = "For ANONYM users ,This API is used for user registration", responses = {
            @ApiResponse(responseCode = "200", description = "User registered", content = @Content(schema = @Schema(implementation = ResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "Bad request", content = @Content(schema = @Schema(implementation = ResponseDTO.class)))})
    @PostMapping("/user/register")
    public ResponseEntity<ResponseDTO<AuthUser>> register(@Valid @RequestBody UserCreateDTO dto) {
        AuthUser authUser = authUserService.create(dto);
        return ResponseEntity.ok(new ResponseDTO<>(authUser, "Registered successfully"));
    }

    @Operation(summary = "For ANONYM users ,This API is used for generate access token", responses = {
            @ApiResponse(responseCode = "200", description = "Access token generated", content = @Content(schema = @Schema(implementation = ResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "Bad request", content = @Content(schema = @Schema(implementation = ResponseDTO.class)))})
    @PostMapping({"/access/token"})
    public ResponseEntity<ResponseDTO<TokenResponse>> generateToken(@Valid @RequestBody TokenRequest tokenRequest) {
        TokenResponse tokenResponse = authUserService.generateToken(tokenRequest);
        tokenResponse.setRole(authUserService.findByPhoneNumber(tokenRequest.phoneNumber()).getRole());
        return ResponseEntity.ok(new ResponseDTO<>(tokenResponse));
    }

    @Operation(summary = "For ANONYM users ,This API is used for generating a new access token using the refresh token", responses = {
            @ApiResponse(responseCode = "200", description = "Access token generated", content = @Content(schema = @Schema(implementation = ResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "Bad request", content = @Content(schema = @Schema(implementation = ResponseDTO.class)))})
    @PostMapping("/refresh/token")
    public ResponseEntity<ResponseDTO<TokenResponse>> refreshToken(@Valid @RequestBody RefreshTokenRequest refreshTokenRequest) {
        TokenResponse tokenResponse = authUserService.refreshToken(refreshTokenRequest);
        return ResponseEntity.ok(new ResponseDTO<>(tokenResponse));
    }


    @Operation(summary = "For ANONYM users ,This API is used for user activating users through the activation code that was sent via SMS", responses = {
            @ApiResponse(responseCode = "200", description = "User activated", content = @Content(schema = @Schema(implementation = ResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "Bad request", content = @Content(schema = @Schema(implementation = ResponseDTO.class)))})
    @PostMapping("/user/activate")
    public ResponseEntity<ResponseDTO<String>> activate(@Valid @RequestBody UserActivationDTO dto) {
        String activated = authUserService.activate(dto);
        return ResponseEntity.ok(new ResponseDTO<>(activated));
    }

    @Operation(summary = "For ANONYM users ,This API is used for user activating users through the activation code that was sent via SMS", responses = {
            @ApiResponse(responseCode = "200", description = "User activated", content = @Content(schema = @Schema(implementation = ResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "Bad request", content = @Content(schema = @Schema(implementation = ResponseDTO.class)))})
    @PostMapping("/code/resend")
    public ResponseEntity<ResponseDTO<Void>> resendCode(@NonNull String phoneNumber) {
        authUserService.resendCode(phoneNumber, SMSCodeType.ACTIVATION);
        return ResponseEntity.ok(new ResponseDTO<>(null, "Sms code sent successfully"));
    }

    @Operation(summary = "For ANONYM users ,This API is used for get sms code for reset password", responses = {
            @ApiResponse(responseCode = "200", description = "Sms sent", content = @Content(schema = @Schema(implementation = ResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "Bad request", content = @Content(schema = @Schema(implementation = ResponseDTO.class)))})
    @PostMapping("/forget/password/{phoneNumber:.*}")
    public ResponseEntity<ResponseDTO<Void>> resetPasswordRequest(@PathVariable String phoneNumber) {
        log.info("Reset password request for phone number : {}", phoneNumber);
        authUserService.resendCode(phoneNumber, SMSCodeType.FORGET_PASSWORD);
        return ResponseEntity.ok(new ResponseDTO<>(null, "Sms code sent successfully"));
    }

    @Operation(summary = "For ANONYM users ,This API is used for reset password", responses = {
            @ApiResponse(responseCode = "200", description = "Password reset", content = @Content(schema = @Schema(implementation = ResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "Bad request", content = @Content(schema = @Schema(implementation = ResponseDTO.class)))})
    @PostMapping("/reset/password")
    public ResponseEntity<ResponseDTO<Void>> resetPassword(@RequestBody UserResetPasswordDTO dto) {
        authUserService.resetPassword(dto);
        return ResponseEntity.ok(new ResponseDTO<>(null, "Password reset successfully"));
    }
}

