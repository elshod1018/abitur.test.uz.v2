package uz.test.abitur.dtos.subject;

import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.springdoc.core.annotations.ParameterObject;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SubjectUpdateDTO {
    private Integer id;

    private String name;

    private String code;

    private boolean mandatory;
}
