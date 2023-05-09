package uz.test.abitur.dtos.user;

import lombok.*;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.web.multipart.MultipartFile;
import uz.test.abitur.enums.Role;
import uz.test.abitur.enums.Status;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserProfileUpdateDTO {
    private String id;

    private String firstName;

    private String middleName;

    private String lastName;
}
