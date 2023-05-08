package uz.test.abitur.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import uz.test.abitur.domains.UserSMS;
import uz.test.abitur.enums.SMSCodeType;

public interface UserSMSRepository extends JpaRepository<UserSMS, Integer> {
    @Query("""
            select u from UserSMS u
            where u.expired = false and
            u.type = ?1 and
            u.userId = ?2 and
            u.toTime > CURRENT_TIMESTAMP""")
    UserSMS findByUserId(SMSCodeType type,String userId);
}