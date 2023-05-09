package uz.test.abitur.dtos.question;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class QuestionUpdateDTO {
    private String id;
    private Integer subjectId;
    private String text;
    private AnswerCreateDTO firstAnswer;
    private AnswerCreateDTO secondAnswer;
    private AnswerCreateDTO thirdAnswer;
    private AnswerCreateDTO fourthAnswer;
}
