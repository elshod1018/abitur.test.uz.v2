package uz.test.abitur.domains;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UuidGenerator;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Question extends Auditable {
    @Id
    @UuidGenerator
    private String id;

    @ManyToOne(optional = false)
    private Subject subject;

    @Column(nullable = false)
    private String text;

    @Builder(builderMethodName = "childBuilder")
    public Question(String createdBy, String updateBy, LocalDateTime createdAt, LocalDateTime updatedAt, boolean deleted, String id, Subject subject, String text) {
        super(createdBy, updateBy, createdAt, updatedAt, deleted);
        this.id = id;
        this.subject = subject;
        this.text = text;
    }
}
