package uz.test.abitur.domains;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TestSession implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false,updatable = false)
    private String userId;

    @Column(nullable = false,updatable = false)
    private LocalDateTime startedAt;

    private LocalDateTime finishedAt;

    private Integer firstSubjectId;

    private Integer secondSubjectId;

    private Integer thirdSubjectId;

    private Integer fourthSubjectId;

    private Integer fifthSubjectId;

    private boolean finished;
}
