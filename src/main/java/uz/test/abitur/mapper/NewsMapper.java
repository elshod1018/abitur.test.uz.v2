package uz.test.abitur.mapper;


import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;
import uz.test.abitur.domains.News;
import uz.test.abitur.dtos.news.NewsCreateDTO;
import uz.test.abitur.dtos.news.NewsUpdateDTO;

@Mapper(componentModel = "spring")
public interface NewsMapper {
    NewsMapper NEWS_MAPPER = Mappers.getMapper(NewsMapper.class);

    NewsCreateDTO toDto(News news);
    News toEntity(NewsCreateDTO dto);
    @BeanMapping(nullValuePropertyMappingStrategy = org.mapstruct.NullValuePropertyMappingStrategy.IGNORE)
    void updateNewsFromDTO(NewsUpdateDTO dto, @MappingTarget News news);
}
