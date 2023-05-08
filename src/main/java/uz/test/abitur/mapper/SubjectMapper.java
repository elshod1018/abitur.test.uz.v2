package uz.test.abitur.mapper;


import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;
import uz.test.abitur.domains.News;
import uz.test.abitur.domains.Subject;
import uz.test.abitur.dtos.news.NewsCreateDTO;
import uz.test.abitur.dtos.news.NewsUpdateDTO;
import uz.test.abitur.dtos.subject.SubjectCreateDTO;
import uz.test.abitur.dtos.subject.SubjectUpdateDTO;

@Mapper(componentModel = "spring")
public interface SubjectMapper {
    SubjectMapper SUBJECT_MAPPER = Mappers.getMapper(SubjectMapper.class);

    SubjectCreateDTO toDto(Subject subject);
    Subject toEntity(SubjectCreateDTO dto);
    @BeanMapping(nullValuePropertyMappingStrategy = org.mapstruct.NullValuePropertyMappingStrategy.IGNORE)
    void updateNewsFromDTO(SubjectUpdateDTO dto, @MappingTarget Subject subject);
}
