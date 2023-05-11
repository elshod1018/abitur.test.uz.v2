package uz.test.abitur.evenet_listeners.listeners;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionalEventListener;
import uz.test.abitur.domains.TestSession;
import uz.test.abitur.evenet_listeners.events.SendSMSEvent;
import uz.test.abitur.evenet_listeners.events.TestSessionCreatedEvent;
import uz.test.abitur.services.SolveQuestionService;
import uz.test.abitur.services.TwilioService;

import java.util.Objects;

@Component
@RequiredArgsConstructor
public class TestSessionEventListener {
    private final SolveQuestionService solveQuestionService;

    @Async
    @TransactionalEventListener(value = TestSessionCreatedEvent.class)
    public void testSessionCreatedEventListener(TestSessionCreatedEvent event) {
        TestSession testSession = event.getTestSession();
        if (!Objects.isNull(testSession)) {
            solveQuestionService.create(testSession);
        }
    }
}
