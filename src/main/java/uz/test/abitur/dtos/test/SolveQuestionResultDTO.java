package uz.test.abitur.dtos.test;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import lombok.*;
import uz.test.abitur.domains.Question;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SolveQuestionResultDTO {

    private Question question;

    private String userAnswerId;
}
