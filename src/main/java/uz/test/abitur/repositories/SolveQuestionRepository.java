package uz.test.abitur.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import uz.test.abitur.domains.SolveQuestion;

public interface SolveQuestionRepository extends JpaRepository<SolveQuestion, Integer> {
    @Query("select count(s) from SolveQuestion s where s.testSessionId = ?1 and s.subjectId = ?2")
    int countByTestSessionIdAndSubjectId(Integer testSessionId, Integer firstSubjectId);
}