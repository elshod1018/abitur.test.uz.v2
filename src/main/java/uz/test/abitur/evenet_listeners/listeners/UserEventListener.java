package uz.test.abitur.evenet_listeners.listeners;

import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionalEventListener;
import uz.test.abitur.evenet_listeners.events.UserCreatedEvent;
import uz.test.abitur.services.TwilioService;

import java.util.Objects;

@Component
@RequiredArgsConstructor
public class UserEventListener {
    private final TwilioService twilioService;

    @Async
    @TransactionalEventListener(value = UserCreatedEvent.class)
    public void userCreatedEventListener(UserCreatedEvent event) {
        String phoneNumber = event.getPhoneNumber();
        String smsCode = event.getSmsCode();
        if (!Objects.isNull(phoneNumber) && !Objects.isNull(smsCode)) {
            System.out.println("Sms successfully send to: " + phoneNumber + " with code: " + smsCode);
//            twilioService.sendSMS(phoneNumber, smsCode);
        }
    }
}
