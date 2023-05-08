package uz.test.abitur.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import uz.test.abitur.domains.News;

import java.util.Optional;
@Repository
public interface NewsRepository extends JpaRepository<News, Integer> {

    @Query("select n from News n where n.deleted = false and n.id = ?1")
    Optional<News> findNewsById(Integer id);
}