package uz.test.abitur.mapper;

import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;
import uz.test.abitur.domains.News;
import uz.test.abitur.dtos.news.NewsCreateDTO;
import uz.test.abitur.dtos.news.NewsUpdateDTO;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-05-10T13:35:01+0500",
    comments = "version: 1.5.3.Final, compiler: javac, environment: Java 17.0.6 (Private Build)"
)
@Component
public class NewsMapperImpl implements NewsMapper {

    @Override
    public NewsCreateDTO toDto(News news) {
        if ( news == null ) {
            return null;
        }

        NewsCreateDTO.NewsCreateDTOBuilder newsCreateDTO = NewsCreateDTO.builder();

        newsCreateDTO.title( news.getTitle() );
        newsCreateDTO.body( news.getBody() );

        return newsCreateDTO.build();
    }

    @Override
    public News toEntity(NewsCreateDTO dto) {
        if ( dto == null ) {
            return null;
        }

        News.NewsBuilder news = News.childBuilder();

        news.title( dto.getTitle() );
        news.body( dto.getBody() );

        return news.build();
    }

    @Override
    public void updateNewsFromDTO(NewsUpdateDTO dto, News news) {
        if ( dto == null ) {
            return;
        }

        if ( dto.getId() != null ) {
            news.setId( dto.getId() );
        }
        if ( dto.getTitle() != null ) {
            news.setTitle( dto.getTitle() );
        }
        if ( dto.getBody() != null ) {
            news.setBody( dto.getBody() );
        }
    }
}
