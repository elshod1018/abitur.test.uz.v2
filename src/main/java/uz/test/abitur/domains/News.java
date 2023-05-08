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
public class News extends Auditable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(length = 50,nullable = false)
    private String title;

    @Column(length = 1000, nullable = false)
    private String body;

    @Builder(builderMethodName = "childBuilder")
    public News(String createdBy, String updateBy, LocalDateTime createdAt, LocalDateTime updatedAt, boolean deleted,
                Integer id, String title, String body) {
        super(createdBy, updateBy, createdAt, updatedAt, deleted);
        this.id = id;
        this.title = title;
        this.body = body;
    }
}
