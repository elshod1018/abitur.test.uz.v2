package uz.test.abitur.dtos.user;

import jakarta.persistence.Enumerated;
import lombok.*;
import org.springdoc.core.annotations.ParameterObject;
import uz.test.abitur.enums.Role;
import uz.test.abitur.enums.Status;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ParameterObject
public class UserUpdateDTO {
    private String id;

    private Role role;

    private Status status;
}
