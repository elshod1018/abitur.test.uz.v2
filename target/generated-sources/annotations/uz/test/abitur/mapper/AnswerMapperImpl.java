package uz.test.abitur.mapper;

import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;
import uz.test.abitur.domains.Answer;
import uz.test.abitur.dtos.question.AnswerCreateDTO;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-05-10T13:35:01+0500",
    comments = "version: 1.5.3.Final, compiler: javac, environment: Java 17.0.6 (Private Build)"
)
@Component
public class AnswerMapperImpl implements AnswerMapper {

    @Override
    public Answer toEntity(AnswerCreateDTO dto) {
        if ( dto == null ) {
            return null;
        }

        Answer.AnswerBuilder answer = Answer.builder();

        answer.text( dto.getText() );

        return answer.build();
    }
}
