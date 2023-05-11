package uz.test.abitur.services;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import uz.test.abitur.config.security.SessionUser;
import uz.test.abitur.domains.SolveQuestion;
import uz.test.abitur.domains.Subject;
import uz.test.abitur.domains.TestSession;
import uz.test.abitur.dtos.test.TestSessionCreateDTO;
import uz.test.abitur.evenet_listeners.events.TestSessionCreatedEvent;
import uz.test.abitur.repositories.SolveQuestionRepository;
import uz.test.abitur.repositories.TestSessionRepository;
import uz.test.abitur.utils.BaseUtils;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class SolveQuestionService {
    private final SolveQuestionRepository solveQuestionRepository;
    private final QuestionService questionService;

    public void create(TestSession testSession) {
        Integer firstSubjectId = testSession.getFirstSubjectId();
        Integer secondSubjectId = testSession.getSecondSubjectId();
        Integer thirdSubjectId = testSession.getThirdSubjectId();
        Integer fourthSubjectId = testSession.getFourthSubjectId();
        Integer fifthSubjectId = testSession.getFifthSubjectId();
        Integer testSessionId = testSession.getId();
        List<String> subjectQuestions;
        if (Objects.nonNull(firstSubjectId)) {
            subjectQuestions = questionService.findNRandQuestionsBySubjectId(firstSubjectId, BaseUtils.QUESTION_COUNT_MAP.get("mainSubject"));
            save(firstSubjectId, testSessionId, subjectQuestions);
        }
        if (Objects.nonNull(secondSubjectId)) {
            subjectQuestions = questionService.findNRandQuestionsBySubjectId(secondSubjectId, BaseUtils.QUESTION_COUNT_MAP.get("mainSubject"));
            save(secondSubjectId, testSessionId, subjectQuestions);
        }
        if (Objects.nonNull(thirdSubjectId)) {
            subjectQuestions = questionService.findNRandQuestionsBySubjectId(thirdSubjectId, BaseUtils.QUESTION_COUNT_MAP.get("mandatorySubject"));
            save(thirdSubjectId, testSessionId, subjectQuestions);
        }
        if (Objects.nonNull(fourthSubjectId)) {
            subjectQuestions = questionService.findNRandQuestionsBySubjectId(fourthSubjectId, BaseUtils.QUESTION_COUNT_MAP.get("mandatorySubject"));
            save(fourthSubjectId, testSessionId, subjectQuestions);
        }
        if (Objects.nonNull(fifthSubjectId)) {
            subjectQuestions = questionService.findNRandQuestionsBySubjectId(fifthSubjectId, BaseUtils.QUESTION_COUNT_MAP.get("mandatorySubject"));
            save(fifthSubjectId, testSessionId, subjectQuestions);
        }
    }

    private void save(Integer subjectId, Integer testSessionId, List<String> questionsIds) {
        questionsIds.forEach(questionId -> {
            solveQuestionRepository.save(SolveQuestion.builder()
                    .questionId(questionId)
                    .subjectId(subjectId)
                    .testSessionId(testSessionId)
                    .build());
        });
    }
}
