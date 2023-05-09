package uz.test.abitur.dtos.question;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AnswerCreateDTO {
    @NotBlank(message = "Text can't be empty")
    private String text;

    @JsonProperty("is_true")
    private boolean isTrue;
}

