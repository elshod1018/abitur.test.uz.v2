package uz.test.abitur.services;

import lombok.SneakyThrows;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.annotation.Lazy;
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
import java.util.concurrent.TimeUnit;

@Service
public class QuestionService {
    private final QuestionRepository questionRepository;
    private final SubjectService subjectService;
    private final AnswerService answerService;

    public QuestionService(QuestionRepository questionRepository,
                           @Lazy SubjectService subjectService,
                           AnswerService answerService) {
        this.questionRepository = questionRepository;
        this.subjectService = subjectService;
        this.answerService = answerService;
    }

    /*@CacheEvict(value = "questionList", allEntries = true)*/
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

   /* @CachePut(value = "questions", key = "#dto.id")*/
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

    /*@Cacheable(value = "questions", key = "#id")*/
    public Question getById(String id) {
        return questionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Question not found with id: %s".formatted(id)));
    }

   /* @Cacheable(value = "questionList", key = "#subjectId+'_'+#pageable.pageNumber")*/
    public Page<Question> getAll(Integer subjectId, Pageable pageable) {
        if (subjectId != null) {
            return questionRepository.getAllBySubjectId(subjectId, pageable);
        }
        return questionRepository.getAll(pageable);
    }

   /* @CacheEvict(cacheNames = {"questions", "questionList"}, key = "#id", allEntries = true)*/
    public void delete(String id) {
        Question question = getById(id);
        question.setDeleted(true);
        questionRepository.save(question);
    }

    public List<String> findNRandQuestionsBySubjectId(Integer subjectId, Integer count) {
        return questionRepository.findNRandomBySubjectId(subjectId, count);
    }

    public boolean existsBySubjectId(Integer id) {
        return questionRepository.existsBySubjectId(id);
    }
}
