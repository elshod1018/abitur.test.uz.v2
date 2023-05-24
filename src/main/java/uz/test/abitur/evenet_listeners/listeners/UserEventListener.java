package uz.test.abitur.evenet_listeners.listeners;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionalEventListener;
import uz.test.abitur.evenet_listeners.events.SendSMSEvent;
import uz.test.abitur.services.TwilioService;

import java.util.Objects;

@Slf4j
@Component
@RequiredArgsConstructor
public class UserEventListener {
    private final TwilioService twilioService;

    @Async
    @EventListener(value = SendSMSEvent.class)
    public void sendSMSEventListener(SendSMSEvent event) {
        String phoneNumber = event.getPhoneNumber();
        String smsCode = event.getSmsCode();
        if (!Objects.isNull(phoneNumber) && !Objects.isNull(smsCode)) {
            log.info("Sms send to {}", phoneNumber);
//            twilioService.sendSMS(phoneNumber, smsCode);
        }
    }
}
