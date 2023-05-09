package uz.test.abitur.dtos.auth;

import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.springdoc.core.annotations.ParameterObject;

import java.io.Serializable;

public record UserActivationDTO(@NotBlank(message = "Phone Number can not be blank") String phoneNumber,
                                @NotBlank(message = "Code can not be blank") String code) {
}
