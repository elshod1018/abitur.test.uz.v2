package uz.test.abitur.dtos.news;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.springdoc.core.annotations.ParameterObject;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NewsCreateDTO {

    private String title;

    private String body;
}
