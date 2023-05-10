package uz.test.abitur.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import uz.test.abitur.domains.Document;

import java.util.Optional;

public interface DocumentRepository extends JpaRepository<Document, Integer> {
    @Query("select d from Document d where d.deleted = false and d.generatedName = ?1")
    Optional<Document> findByGeneratedName(String generatedName);

    @Query("select d from Document d where d.deleted = false and d.id = ?1")
    Optional<Document> findDocumetById(Integer id);
}