package uz.test.abitur.domains;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;
import org.hibernate.annotations.UuidGenerator;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Answer {
    @Id
    @UuidGenerator
    private String id;

    @Column(nullable = false,updatable = false)
    private String questionId;

    @Column(nullable = false)
    private String text;

    @Builder.Default
    private Boolean isTrue = false;

}
