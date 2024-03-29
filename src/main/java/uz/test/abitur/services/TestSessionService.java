package uz.test.abitur.services;

import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import uz.test.abitur.config.security.SessionUser;
import uz.test.abitur.domains.Subject;
import uz.test.abitur.domains.TestSession;
import uz.test.abitur.dtos.test.TestSessionCreateDTO;
import uz.test.abitur.evenet_listeners.events.TestSessionCreatedEvent;
import uz.test.abitur.evenet_listeners.events.TestSessionFinishedEvent;
import uz.test.abitur.repositories.TestSessionRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
@Service
@RequiredArgsConstructor
public class TestSessionService {
    private final SessionUser sessionUser;
    private final SubjectService subjectService;
    private final TestSessionRepository testSessionRepository;
    private final ApplicationEventPublisher applicationEventPublisher;

    public TestSession create(TestSessionCreateDTO dto) {
        Integer firstSubjectId = dto.getFirstSubjectId();
        Integer secondSubjectId = dto.getSecondSubjectId();
        boolean withMandatory = dto.isWithMandatory();
        if (firstSubjectId == null && secondSubjectId == null && !withMandatory) {
            throw new RuntimeException("You should choose at least one subject");
        }
        List<Subject> mandatorySubjects = subjectService.getMandatorySubjects();

        TestSession testSession = new TestSession();
        testSession.setStartedAt(LocalDateTime.now());
        testSession.setFinishedAt(LocalDateTime.now());
        testSession.setUserId(sessionUser.id());
        if (!Objects.isNull(firstSubjectId)) {
            Subject firstSubject = subjectService.findById(firstSubjectId);
            testSession.setFirstSubjectId(firstSubjectId);
            testSession.setFinishedAt(testSession.getFinishedAt().plusHours(1));
        }
        if (!Objects.isNull(secondSubjectId)) {
            Subject secondSubject = subjectService.findById(secondSubjectId);
            testSession.setSecondSubjectId(secondSubjectId);
            testSession.setFinishedAt(testSession.getFinishedAt().plusHours(1));
        }
        if (withMandatory) {
            if (mandatorySubjects.size() == 3) {
                testSession.setFinishedAt(testSession.getFinishedAt().plusHours(1));
                testSession.setThirdSubjectId(mandatorySubjects.get(0).getId());
                testSession.setFourthSubjectId(mandatorySubjects.get(1).getId());
                testSession.setFifthSubjectId(mandatorySubjects.get(2).getId());
            } else {
                throw new RuntimeException("Error with mandatory subjects");
            }
        }
        testSessionRepository.save(testSession);
        applicationEventPublisher.publishEvent(new TestSessionCreatedEvent(testSession));
        return testSession;
    }

    public TestSession findActiveTestSession() {
        return testSessionRepository.findByUserId(sessionUser.id());
    }

    public void finishTest(TestSession testSession) {
        testSession.setFinished(true);
        testSession.setFinishedAt(LocalDateTime.now());
        testSessionRepository.save(testSession);
        applicationEventPublisher.publishEvent(new TestSessionFinishedEvent(testSession));
    }

    public TestSession findById(Integer testSessionId) {
        return testSessionRepository.findTestSessionById(testSessionId)
                .orElseThrow(() -> new RuntimeException("Test session not found by id " + testSessionId));
    }
}
