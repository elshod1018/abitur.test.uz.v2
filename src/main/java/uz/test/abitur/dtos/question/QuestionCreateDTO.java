package uz.test.abitur.dtos.question;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class QuestionCreateDTO {
    private Integer subjectId;
    @NotBlank(message = "Text can't be blank")
    private String text;
    @NotNull(message = "First answer can't be null")
    private AnswerCreateDTO firstAnswer;
    @NotNull(message = "Second answer can't be null")
    private AnswerCreateDTO secondAnswer;
    @NotNull(message = "Third answer can't be null")
    private AnswerCreateDTO thirdAnswer;
    @NotNull(message = "Fourth answer can't be null")
    private AnswerCreateDTO fourthAnswer;
}
