package uz.test.abitur.services;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import uz.test.abitur.domains.News;
import uz.test.abitur.dtos.news.NewsCreateDTO;
import uz.test.abitur.dtos.news.NewsUpdateDTO;
import uz.test.abitur.repositories.NewsRepository;

import static uz.test.abitur.mapper.NewsMapper.NEWS_MAPPER;

@Service
@RequiredArgsConstructor
public class NewsService {

    private final NewsRepository newsRepository;

    public News create(NewsCreateDTO dto) {
        News news = NEWS_MAPPER.toEntity(dto);
        return newsRepository.save(news);
    }

    public News update(NewsUpdateDTO dto) {
        News news = findById(dto.getId());
        NEWS_MAPPER.updateNewsFromDTO(dto, news);
        return newsRepository.save(news);
    }

    public News findById(Integer id) {
        return newsRepository.findNewsById(id)
                .orElseThrow(() -> new RuntimeException("News not found with id: " + id));
    }

    public void delete(Integer id) {
        News news = findById(id);
        news.setDeleted(true);
        newsRepository.save(news);
    }

    public Page<News> getAll(Pageable pageable) {
        return newsRepository.getAll(pageable);
    }
}
