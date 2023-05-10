package uz.test.abitur.dtos.auth;

import jakarta.validation.constraints.NotBlank;

public record UserActivationDTO(@NotBlank(message = "Phone Number can not be blank") String phoneNumber,
                                @NotBlank(message = "Code can not be blank") String code) {
}
