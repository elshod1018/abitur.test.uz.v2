package uz.test.abitur.services;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import uz.test.abitur.domains.News;
import uz.test.abitur.dtos.news.NewsCreateDTO;
import uz.test.abitur.dtos.news.NewsUpdateDTO;
import uz.test.abitur.repositories.NewsRepository;

import java.util.concurrent.TimeUnit;

import static uz.test.abitur.mapper.NewsMapper.NEWS_MAPPER;

@Service
@RequiredArgsConstructor
public class NewsService {

    private final NewsRepository newsRepository;

    @CacheEvict(value = "newsList", allEntries = true)
    public News create(NewsCreateDTO dto) {
        News news = NEWS_MAPPER.toEntity(dto);
        return newsRepository.save(news);
    }

    @CachePut(value = "news", key = "#dto.id")
    public News update(NewsUpdateDTO dto) {
        News news = findById(dto.getId());
        NEWS_MAPPER.updateNewsFromDTO(dto, news);
        return newsRepository.save(news);
    }

    @SneakyThrows
    @Cacheable(value = "news", key = "#id")
    public News findById(Integer id) {
        TimeUnit.SECONDS.sleep(3);
        return newsRepository.findNewsById(id)
                .orElseThrow(() -> new RuntimeException("News not found with id: " + id));
    }

    @CacheEvict(value = "news", key = "#id")
    public void delete(Integer id) {
        News news = findById(id);
        news.setDeleted(true);
        newsRepository.save(news);
    }

    @Cacheable(value = "newsList", key = "#pageable.pageNumber")
    public Page<News> getAll(Pageable pageable) {
        return newsRepository.getAll(pageable);
    }
}
