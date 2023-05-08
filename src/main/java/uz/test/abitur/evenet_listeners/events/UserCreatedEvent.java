package uz.test.abitur.evenet_listeners.events;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public final class UserCreatedEvent {
    private final String phoneNumber;
    private final String smsCode;
}
