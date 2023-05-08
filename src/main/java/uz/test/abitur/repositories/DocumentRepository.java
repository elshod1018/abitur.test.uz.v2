package uz.test.abitur.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.test.abitur.domains.Document;

public interface DocumentRepository extends JpaRepository<Document, Integer> {
}