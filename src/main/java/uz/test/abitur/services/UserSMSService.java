package uz.test.abitur.services;

import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import uz.test.abitur.domains.AuthUser;
import uz.test.abitur.domains.UserSMS;
import uz.test.abitur.enums.SMSCodeType;
import uz.test.abitur.evenet_listeners.events.UserCreatedEvent;
import uz.test.abitur.repositories.UserSMSRepository;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class UserSMSService {
    private final ApplicationEventPublisher applicationEventPublisher;
    private final UserSMSRepository userSMSRepository;

    public UserSMS createSMSCode(AuthUser user, SMSCodeType type) {
        String userId = user.getId();
        UserSMS userSMS = findByUserId(userId, type);
        if (!Objects.isNull(userSMS)) {
            return userSMS;
        }
        String smsCode = "666666"; /*baseUtils.generateCode()*/
        userSMS = save(userId, smsCode);
//        smsCode = smsCode.substring(0, 3) + " " + smsCode.substring(3);
////        twilioService.sendSMS(user.getPhoneNumber(), smsCode);
        applicationEventPublisher.publishEvent(new UserCreatedEvent(user.getPhoneNumber(), smsCode));
        return userSMS;
    }

    private UserSMS save(String userId, String smsCode) {
        UserSMS userSMS = UserSMS.builder()
                .userId(userId)
                .code(smsCode)
                .build();
        return userSMSRepository.save(userSMS);
    }

    public UserSMS findByUserId(String userId, SMSCodeType type) {
        return userSMSRepository.findByUserId( type,userId);
    }

    public UserSMS update(UserSMS userSMS) {
        return userSMSRepository.save(userSMS);
    }
}
