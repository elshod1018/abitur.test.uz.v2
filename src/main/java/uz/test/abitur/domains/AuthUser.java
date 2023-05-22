package uz.test.abitur.domains;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.UuidGenerator;
import uz.test.abitur.enums.Language;
import uz.test.abitur.enums.Role;
import uz.test.abitur.enums.Status;

import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AuthUser extends Auditable implements Serializable {
    @Id
    @UuidGenerator
    private String id;

    @Column(nullable = false)
    private String firstName;

    private String middleName;

    @Column(nullable = false)
    private String lastName;

    @Column(nullable = false)
    private String phoneNumber;

    @JsonIgnore
    @Column(nullable = false)
    private String password;

    private String profilePhotoGeneratedName;

    @Enumerated(EnumType.STRING)
    private Language language;

    @Enumerated(EnumType.STRING)
    private Role role;

    @Enumerated(EnumType.STRING)
    private Status status;

    @Builder(builderMethodName = "childBuilder")
    public AuthUser(String createdBy, String updateBy, LocalDateTime createdAt, LocalDateTime updatedAt, boolean deleted, String id, String firstName, String middleName, String lastName, String phoneNumber, String password, String profilePhotoGeneratedName) {
        super(createdBy, updateBy, createdAt, updatedAt, deleted);
        this.id = id;
        this.firstName = firstName;
        this.middleName = middleName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.password = password;
        this.profilePhotoGeneratedName = profilePhotoGeneratedName;
        this.language = Language.UZ;
        this.role = Role.USER;
        this.status = Status.NO_ACTIVE;
    }
}
