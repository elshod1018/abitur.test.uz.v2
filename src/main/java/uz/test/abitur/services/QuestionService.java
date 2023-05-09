package uz.test.abitur.services;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import uz.test.abitur.domains.Answer;
import uz.test.abitur.domains.Question;
import uz.test.abitur.domains.Subject;
import uz.test.abitur.dtos.question.QuestionCreateDTO;
import uz.test.abitur.dtos.question.QuestionUpdateDTO;
import uz.test.abitur.repositories.QuestionRepository;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class QuestionService {
    private final QuestionRepository questionRepository;
    private final SubjectService subjectService;
    private final AnswerService answerService;

    public Question create(QuestionCreateDTO dto) {
        Subject subject = subjectService.findById(dto.getSubjectId());
        List<Answer> answers = answerService.create(dto);
        Question question = Question.childBuilder()
                .text(dto.getText())
                .subject(subject)
                .answers(answers)
                .build();
        return questionRepository.save(question);
    }

    public Question update(QuestionUpdateDTO dto) {
        Question question = getById(dto.getId());
        Integer dtoSubjectId = dto.getSubjectId();
        if (!Objects.isNull(dtoSubjectId)) {
            question.setSubject(subjectService.findById(dtoSubjectId));
        }
        if (!Objects.isNull(dto.getText())) {
            question.setText(dto.getText());
        }
        List<Answer> answerList = answerService.update(dto, question);
        question.setAnswers(answerList);
        return questionRepository.save(question);
    }


    public Question getById(String id) {
        return questionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Question not found with id: %s".formatted(id)));
    }

    public Page<Question> getAll(Integer subjectId, Pageable pageable) {
        if (subjectId!=null) {
            return questionRepository.getAllBySubjectId(subjectId, pageable);
        }
        return questionRepository.getAll(pageable);
    }

    public void delete(String id) {
        Question question = getById(id);
        question.setDeleted(true);
        questionRepository.save(question);
    }
}
