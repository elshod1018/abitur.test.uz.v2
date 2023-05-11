package uz.test.abitur.domains;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import lombok.*;
@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SolveQuestion{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private Integer testSessionId;

    @Column(nullable = false)
    private Integer subjectId;

    @Column(nullable = false)
    private String questionId;

    private String userAnswerId;
}
