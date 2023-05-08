package uz.test.abitur.domains;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"code", "mandatory"}))
public class Subject extends Auditable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(length = 50, nullable = false)
    private String name;

    @Column(length = 50, nullable = false)
    private String code;

    private boolean mandatory;

    @Builder(builderMethodName = "childBuilder")
    public Subject(String createdBy, String updateBy, LocalDateTime createdAt, LocalDateTime updatedAt, boolean deleted, Integer id, String name, String code, boolean mandatory) {
        super(createdBy, updateBy, createdAt, updatedAt, deleted);
        this.id = id;
        this.name = name;
        this.code = code;
        this.mandatory = mandatory;
    }
}
