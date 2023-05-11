package uz.test.abitur.evenet_listeners.events;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEvent;
import uz.test.abitur.domains.TestSession;

@Getter
@RequiredArgsConstructor
public final class TestHistoryFinishedEvent {
    private final TestSession testSession;
}
