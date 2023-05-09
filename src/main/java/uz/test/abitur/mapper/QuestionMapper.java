package uz.test.abitur.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import uz.test.abitur.domains.Question;
import uz.test.abitur.dtos.question.QuestionCreateDTO;

@Mapper(componentModel = "spring")
public interface QuestionMapper {

    QuestionMapper QUESTION_MAPPER = Mappers.getMapper(QuestionMapper.class);

    Question toEntity(QuestionCreateDTO dto);


}
