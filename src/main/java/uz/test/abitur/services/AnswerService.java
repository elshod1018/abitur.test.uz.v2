package uz.test.abitur.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.test.abitur.domains.Answer;
import uz.test.abitur.domains.Question;
import uz.test.abitur.dtos.question.AnswerCreateDTO;
import uz.test.abitur.dtos.question.QuestionCreateDTO;
import uz.test.abitur.dtos.question.QuestionUpdateDTO;
import uz.test.abitur.repositories.AnswerRepository;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class AnswerService {
    private final AnswerRepository answerRepository;

    public List<Answer> create(QuestionCreateDTO dto) {
        checkingTrueAnswer(dto.getFirstAnswer(), dto.getSecondAnswer(), dto.getThirdAnswer(), dto.getFourthAnswer());
        Answer answer1 = Answer.builder()
                .text(dto.getFirstAnswer().getText())
                .isTrue(dto.getFirstAnswer().isTrue())
                .build();
        Answer answer2 = Answer.builder()
                .text(dto.getSecondAnswer().getText())
                .isTrue(dto.getSecondAnswer().isTrue())
                .build();
        Answer answer3 = Answer.builder()
                .text(dto.getThirdAnswer().getText())
                .isTrue(dto.getThirdAnswer().isTrue())
                .build();
        Answer answer4 = Answer.builder()
                .text(dto.getFourthAnswer().getText())
                .isTrue(dto.getFourthAnswer().isTrue())
                .build();

        return answerRepository.saveAll(List.of(answer1, answer2, answer3, answer4));
    }

    public List<Answer> update(QuestionUpdateDTO dto, Question question) {
        checkingTrueAnswer(dto.getFirstAnswer(), dto.getSecondAnswer(), dto.getThirdAnswer(), dto.getFourthAnswer());
        List<Answer> answers = question.getAnswers();
        Answer answer;
        AnswerCreateDTO firstAnswer = dto.getFirstAnswer();
        boolean aTrue;
        if (!Objects.isNull(firstAnswer)) {
            answer = answers.get(0);
            answer.setText(firstAnswer.getText());
            aTrue = firstAnswer.isTrue();
            if (aTrue)
                setFalse(answers);
            answer.setIsTrue(aTrue);
        }
        AnswerCreateDTO secondAnswer = dto.getSecondAnswer();
        if (!Objects.isNull(secondAnswer)) {
            answer = answers.get(1);
            answer.setText(secondAnswer.getText());
            aTrue = secondAnswer.isTrue();
            if (aTrue)
                setFalse(answers);
            answer.setIsTrue(aTrue);
        }
        AnswerCreateDTO thirdAnswer = dto.getThirdAnswer();
        if (!Objects.isNull(thirdAnswer)) {
            answer = answers.get(2);
            answer.setText(thirdAnswer.getText());
            aTrue = thirdAnswer.isTrue();
            if (aTrue)
                setFalse(answers);
            answer.setIsTrue(aTrue);
        }
        AnswerCreateDTO fourthAnswer = dto.getFourthAnswer();
        if (!Objects.isNull(fourthAnswer)) {
            answer = answers.get(3);
            answer.setText(fourthAnswer.getText());
            aTrue = fourthAnswer.isTrue();
            if (aTrue)
                setFalse(answers);
            answer.setIsTrue(aTrue);
        }
        return answerRepository.saveAll(answers);
    }

    public void checkingTrueAnswer(AnswerCreateDTO dto1, AnswerCreateDTO dto2, AnswerCreateDTO dto3, AnswerCreateDTO dto4) {
        int count = 0;
        if (dto1 != null && dto1.isTrue())
            count++;
        if (dto2 != null && dto2.isTrue())
            count++;
        if (dto3 != null && dto3.isTrue())
            count++;
        if (dto4 != null && dto4.isTrue())
            count++;
        if (count > 1)
            throw new RuntimeException("Only one answer can be true, But there " + count);
    }

    private void setFalse(List<Answer> answers) {
        answers.forEach(answer -> answer.setIsTrue(false));
    }

}
