package uz.test.abitur.dtos.pdf;

import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TestHistoryPDFGenerateDTO {
    private String subjectName;
    private List<Boolean> listOfTrueOrFalseAnswers;
}
