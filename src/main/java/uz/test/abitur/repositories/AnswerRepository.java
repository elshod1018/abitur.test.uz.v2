package uz.test.abitur.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.test.abitur.domains.Answer;

public interface AnswerRepository extends JpaRepository<Answer, String> {
}