package uz.test.abitur.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.*;
import uz.test.abitur.domains.AuthUser;
import uz.test.abitur.dtos.ResponseDTO;
import uz.test.abitur.dtos.auth.*;
import uz.test.abitur.services.AuthUserService;

import static uz.test.abitur.utils.UrlUtils.BASE_AUTH_URL;

@RestController
@RequiredArgsConstructor
@RequestMapping(BASE_AUTH_URL)
@Tag(name = "Authentication", description = "Authentication API")
public class AuthController {
    private final AuthUserService authUserService;

    @Operation(summary = "This API is used for user registration", responses = {
            @ApiResponse(responseCode = "200", description = "User registered", content = @Content(schema = @Schema(implementation = ResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "Bad request", content = @Content(schema = @Schema(implementation = ResponseDTO.class)))})
    @PostMapping("/user/register")
    public ResponseEntity<ResponseDTO<AuthUser>> register(@Valid UserCreateDTO dto) {
        AuthUser authUser = authUserService.create(dto);
        return ResponseEntity.ok(new ResponseDTO<>(authUser, "Registered successfully"));
    }

    @Operation(summary = "This API is used for generate access token", responses = {
            @ApiResponse(responseCode = "200", description = "Access token generated", content = @Content(schema = @Schema(implementation = ResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "Bad request", content = @Content(schema = @Schema(implementation = ResponseDTO.class)))})
    @PostMapping({"/access/token"})
    public ResponseEntity<ResponseDTO<TokenResponse>> generateToken(@Valid TokenRequest tokenRequest) {
        TokenResponse tokenResponse = authUserService.generateToken(tokenRequest);
        return ResponseEntity.ok(new ResponseDTO<>(tokenResponse));
    }

    @Operation(summary = "This API is used for generating a new access token using the refresh token", responses = {
            @ApiResponse(responseCode = "200", description = "Access token generated", content = @Content(schema = @Schema(implementation = ResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "Bad request", content = @Content(schema = @Schema(implementation = ResponseDTO.class)))})
    @PostMapping("/refresh/token")
    public ResponseEntity<ResponseDTO<TokenResponse>> refreshToken(@Valid @RequestBody RefreshTokenRequest refreshTokenRequest) {
        TokenResponse tokenResponse = authUserService.refreshToken(refreshTokenRequest);
        return ResponseEntity.ok(new ResponseDTO<>(tokenResponse));
    }


    @Operation(summary = "This API is used for user activating users through the activation code that was sent via SMS", responses = {
            @ApiResponse(responseCode = "200", description = "User activated", content = @Content(schema = @Schema(implementation = ResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "Bad request", content = @Content(schema = @Schema(implementation = ResponseDTO.class)))})
    @PostMapping("/user/activate")
    public ResponseEntity<ResponseDTO<String>> activate(@NonNull @Valid UserActivationDTO dto) {
        String activated = authUserService.activate(dto);
        return ResponseEntity.ok(new ResponseDTO<>(activated));
    }

    @Operation(summary = "This API is used for user activating users through the activation code that was sent via SMS", responses = {
            @ApiResponse(responseCode = "200", description = "User activated", content = @Content(schema = @Schema(implementation = ResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "Bad request", content = @Content(schema = @Schema(implementation = ResponseDTO.class)))})
    @PostMapping("/code/resend")
    public ResponseEntity<ResponseDTO<String>> resendCode(@NonNull @RequestParam String phoneNumber) {
        authUserService.resendCode(phoneNumber);
        return ResponseEntity.ok(new ResponseDTO<>("Sms code sent successfully"));
    }
}

