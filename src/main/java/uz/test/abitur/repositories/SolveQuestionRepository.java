package uz.test.abitur.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.test.abitur.domains.SolveQuestion;

public interface SolveQuestionRepository extends JpaRepository<SolveQuestion, Integer> {
}