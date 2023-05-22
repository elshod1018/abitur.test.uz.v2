package uz.test.abitur.domains;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UuidGenerator;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Question extends Auditable implements Serializable {
    @Id
    @UuidGenerator
    private String id;

    @ManyToOne(optional = false)
    private Subject subject;

    @Column(nullable = false)
    private String text;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Answer> answers;

    @Builder(builderMethodName = "childBuilder")
    public Question(String createdBy, String updateBy, LocalDateTime createdAt, LocalDateTime updatedAt, boolean deleted, String id, Subject subject, String text, List<Answer> answers) {
        super(createdBy, updateBy, createdAt, updatedAt, deleted);
        this.id = id;
        this.subject = subject;
        this.text = text;
        this.answers = answers;
    }
}
