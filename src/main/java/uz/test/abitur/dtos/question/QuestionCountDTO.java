package uz.test.abitur.dtos.question;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class QuestionCountDTO {
    @Min(value = 1,message = "Main subject id can't be less than {value}")
    @Max(value = 50,message = "Main subject id can't be greater than {value}")
    private Integer mainSubject;

    @Min(value = 1,message = "Mandatory subject id can't be less than 1")
    @Max(value = 30,message = "Mandatory subject id can't be greater than {value}")
    private Integer mandatorySubject;
}
