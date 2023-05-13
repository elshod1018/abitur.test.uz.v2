package uz.test.abitur.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import uz.test.abitur.domains.SolveQuestion;
import uz.test.abitur.dtos.test.SolveQuestionResultDTO;

import java.util.List;
import java.util.Optional;

public interface SolveQuestionRepository extends JpaRepository<SolveQuestion, Integer> {
    @Query("select s from SolveQuestion s where s.testSessionId = ?1 and s.subjectId = ?2")
    List<SolveQuestion> findByTestSessionIdAndSubjectId(Integer testSessionId, Integer subjectId);

    @Query("""
            select count(s) from SolveQuestion s
            join Answer a on a.id = s.userAnswerId
            where s.testSessionId = ?1 and s.subjectId = ?2 and a.isTrue = true""")
    int getCount(Integer testSessionId, Integer subjectId);

    @Query("""
            select new uz.test.abitur.dtos.test.SolveQuestionResultDTO(q,s.userAnswerId) from SolveQuestion s
            join Question q on s.questionId = q.id
            where s.testSessionId = ?1 and s.subjectId = ?2""")
    Page<SolveQuestionResultDTO> getPageableSolveQuestionDTO(Integer testSessionId,
                                                             Integer subjectId,
                                                             Pageable pageable);

    @Query("select s from SolveQuestion s where s.testSessionId = ?1 and s.subjectId = ?2 and s.questionId = ?3")
    Optional<SolveQuestion> findByTestSessionIdAndSubjectIdAndQuestionId(Integer testSessionId, Integer subjectId, String questionId);
}