package uz.test.abitur.evenet_listeners.events;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public final class SendSMSEvent {
    private final String phoneNumber;
    private final String smsCode;
}
