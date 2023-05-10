package uz.test.abitur.mapper;

import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;
import uz.test.abitur.domains.Question;
import uz.test.abitur.dtos.question.QuestionCreateDTO;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-05-10T13:35:01+0500",
    comments = "version: 1.5.3.Final, compiler: javac, environment: Java 17.0.6 (Private Build)"
)
@Component
public class QuestionMapperImpl implements QuestionMapper {

    @Override
    public Question toEntity(QuestionCreateDTO dto) {
        if ( dto == null ) {
            return null;
        }

        Question.QuestionBuilder question = Question.childBuilder();

        question.text( dto.getText() );

        return question.build();
    }
}
