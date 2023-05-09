package uz.test.abitur.dtos.auth;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.springdoc.core.annotations.ParameterObject;
import uz.test.abitur.customAnnotations.annotations.UniquePhoneNumber;

import java.io.Serializable;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public final class UserCreateDTO implements Serializable {
    @NotBlank(message = "First Name can not be blank")
    private String firstName;

    @NotBlank(message = "Last Name can not be blank")
    private String lastName;

    @UniquePhoneNumber
    @NotBlank(message = "Phone Number can not be blank")
    @Pattern(regexp = "^(\\+998|8)[ -]?\\d{2}[ -]?\\d{3}[ -]?\\d{2}[ -]?\\d{2}$")
    private String phoneNumber;

    @Size(min = 8, max = 16, message = "Password must be between {min} and {max} character")
    @NotBlank(message = "Password can not be blank")
    private String password;

    @Size(min = 8, max = 16, message = "Password must be between {min} and {max} character")
    @NotBlank(message = "Confirm Password can not be blank")
    private String confirmPassword;


}
