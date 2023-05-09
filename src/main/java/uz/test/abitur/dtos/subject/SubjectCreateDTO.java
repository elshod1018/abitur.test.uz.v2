package uz.test.abitur.dtos.subject;

import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.springdoc.core.annotations.ParameterObject;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SubjectCreateDTO {
    @NotBlank(message = "Name can't be blank")
    private String name;

    @NotBlank(message = "Code can't be blank")
    private String code;

    private boolean mandatory;
}
