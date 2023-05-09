package uz.test.abitur.dtos.news;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.springdoc.core.annotations.ParameterObject;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class NewsUpdateDTO {
    private Integer id;

    private String title;

    private String body;

}
