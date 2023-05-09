package uz.test.abitur.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import uz.test.abitur.domains.Answer;
import uz.test.abitur.dtos.question.AnswerCreateDTO;

@Mapper(componentModel = "spring")
public interface AnswerMapper {

    AnswerMapper ANSWER_MAPPER = Mappers.getMapper(AnswerMapper.class);


    Answer toEntity(AnswerCreateDTO dto);


}
