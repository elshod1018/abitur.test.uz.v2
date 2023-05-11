package uz.test.abitur.services;

import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import uz.test.abitur.config.security.SessionUser;
import uz.test.abitur.domains.Subject;
import uz.test.abitur.domains.TestHistory;
import uz.test.abitur.domains.TestSession;
import uz.test.abitur.dtos.test.TestSessionCreateDTO;
import uz.test.abitur.evenet_listeners.events.TestHistoryFinishedEvent;
import uz.test.abitur.evenet_listeners.events.TestSessionCreatedEvent;
import uz.test.abitur.repositories.TestHistoryRepository;
import uz.test.abitur.repositories.TestSessionRepository;
import uz.test.abitur.utils.BaseUtils;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class TestHistoryService {
    private final SubjectService subjectService;
    private final SolveQuestionService solveQuestionService;
    private final TestHistoryRepository testHistoryRepository;
    private final ApplicationEventPublisher applicationEventPublisher;

    public TestHistory create(TestSession testSession) {
        Integer testSessionId = testSession.getId();
        Integer firstSubjectId = testSession.getFirstSubjectId();
        Integer secondSubjectId = testSession.getSecondSubjectId();
        Integer thirdSubjectId = testSession.getThirdSubjectId();
        Integer fourthSubjectId = testSession.getFourthSubjectId();
        Integer fifthSubjectId = testSession.getFifthSubjectId();
        return testHistoryRepository.save(
                TestHistory.builder()
                        .userId(testSession.getUserId())
                        .testSessionId(testSessionId)
                        .startedAt(testSession.getStartedAt())
                        .finishedAt(testSession.getFinishedAt())
                        .firstSubject(firstSubjectId != null ? subjectService.findById(firstSubjectId).getName() + "  " + 0 + "/30" : "")
                        .secondSubject(secondSubjectId != null ? subjectService.findById(secondSubjectId).getName() + "  " + 0 + "/30" : "")
                        .thirdSubject(thirdSubjectId != null ? subjectService.findById(thirdSubjectId).getName() + "  " + 0 + "/10" : "")
                        .fourthSubject(fourthSubjectId != null ? subjectService.findById(fourthSubjectId).getName() + "  " + 0 + "/10" : "")
                        .fifthSubject(fifthSubjectId != null ? subjectService.findById(fifthSubjectId).getName() + "  " + 0 + "/10" : "")
                        .totalScore(0.0f)
                        .build()
        );
    }

    public TestHistory finish(TestSession testSession) {
        Integer testSessionId = testSession.getId();
        Integer firstSubjectId = testSession.getFirstSubjectId();
        Integer secondSubjectId = testSession.getSecondSubjectId();
        Integer thirdSubjectId = testSession.getThirdSubjectId();
        Integer fourthSubjectId = testSession.getFourthSubjectId();
        Integer fifthSubjectId = testSession.getFifthSubjectId();
        int firstCount = solveQuestionService.getCount(testSessionId, firstSubjectId);
        int secondCount = solveQuestionService.getCount(testSessionId, secondSubjectId);
        int thirdCount = solveQuestionService.getCount(testSessionId, thirdSubjectId);
        int fourthCount = solveQuestionService.getCount(testSessionId, fourthSubjectId);
        int fifthCount = solveQuestionService.getCount(testSessionId, fifthSubjectId);
        float firstScore = firstCount * 3.1f;
        float secondScore = secondCount * 2.1f;
        float thirdScore = thirdCount * 1.1f;
        float fourthScore = fourthCount * 1.1f;
        float fifthScore = fifthCount * 1.1f;
        float totalScore = firstScore + secondScore + thirdScore + fourthScore + fifthScore;

        TestHistory testHistory = testHistoryRepository.findByTestSessionId(testSessionId);
        testHistory.setFinishedAt(testSession.getFinishedAt());

        Integer mainSubject = BaseUtils.QUESTION_COUNT_MAP.get("mainSubject");
        Integer mandatorySubject = BaseUtils.QUESTION_COUNT_MAP.get("mandatorySubject");
        if (firstSubjectId != null) {
            String firstSubjectName = subjectService.findById(firstSubjectId).getName();
            testHistory.setFirstSubject("%s   %s/%s".formatted(firstSubjectName, firstCount, mainSubject));
        }
        if (secondSubjectId != null) {
            String secondSubjectName = subjectService.findById(secondSubjectId).getName();
            testHistory.setSecondSubject("%s   %s/%s".formatted(secondSubjectName, secondCount, mainSubject));
        }
        if (thirdSubjectId != null) {
            String thirdSubjectName = subjectService.findById(thirdSubjectId).getName();
            testHistory.setThirdSubject("%s   %s/%s".formatted(thirdSubjectName, thirdCount, mandatorySubject));
        }
        if (fourthSubjectId != null) {
            String fourthSubjectName = subjectService.findById(fourthSubjectId).getName();
            testHistory.setFourthSubject("%s   %s/%s".formatted(fourthSubjectName, fourthCount, mandatorySubject));
        }
        if (fifthSubjectId != null) {
            String fifthSubjectName = subjectService.findById(fifthSubjectId).getName();
            testHistory.setFifthSubject("%s   %s/%s".formatted(fifthSubjectName, fifthCount, mandatorySubject));
        }
        testHistory.setTotalScore(totalScore);
        testHistoryRepository.save(testHistory);
        applicationEventPublisher.publishEvent(new TestHistoryFinishedEvent(testSession));
        return testHistory;
    }

}
