package uz.test.abitur.evenet_listeners.events;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEvent;
import org.springframework.stereotype.Component;
import uz.test.abitur.domains.TestSession;
@Getter
@RequiredArgsConstructor
public final class TestSessionCreatedEvent {
    private final TestSession testSession;
}
