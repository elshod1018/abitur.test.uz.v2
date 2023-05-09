package uz.test.abitur.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import uz.test.abitur.domains.Question;

public interface QuestionRepository extends JpaRepository<Question, String> {
    @Query("select q from Question q where q.deleted = false and q.subject.id = ?1")
    Page<Question> getAllBySubjectId(Integer subjectId, Pageable pageable);

    @Query("select q from Question q where q.deleted = false")
    Page<Question> getAll(Pageable pageable);
}