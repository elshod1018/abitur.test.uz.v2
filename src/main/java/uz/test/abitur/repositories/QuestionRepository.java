package uz.test.abitur.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.test.abitur.domains.Question;

public interface QuestionRepository extends JpaRepository<Question, String> {
}