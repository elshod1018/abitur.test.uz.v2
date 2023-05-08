package uz.test.abitur.domains;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import uz.test.abitur.enums.SMSCodeType;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserSMS {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, updatable = false)
    private String code;

    @Column(nullable = false, updatable = false)
    private String userId;

    @Builder.Default
    @Enumerated(EnumType.STRING)
    private SMSCodeType type = SMSCodeType.ACTIVATION;

    @CreationTimestamp
    @Column(columnDefinition = "timestamp default now()", updatable = false)
    private LocalDateTime fromTime;

    @Builder.Default
    @Column(columnDefinition = "timestamp default now()+INTERVAL '2 Minutes'")
    private LocalDateTime toTime = LocalDateTime.now().plusMinutes(2);

    private boolean expired;
}
