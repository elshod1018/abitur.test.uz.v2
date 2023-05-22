package uz.test.abitur.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import uz.test.abitur.domains.Question;

import java.util.List;

public interface QuestionRepository extends JpaRepository<Question, String> {
    @Query(nativeQuery = true, value = """  
            select q.id from abitur.public.question q where q.deleted = false and q.subject_id = ?1 order by RANDOM() limit ?2""")
    List<String> findNRandomBySubjectId(Integer id, Integer count);

    @Query("select q from Question q where q.deleted = false and q.subject.id = ?1")
    Page<Question> getAllBySubjectId(Integer subjectId, Pageable pageable);

    @Query("select q from Question q where q.deleted = false")
    Page<Question> getAll(Pageable pageable);

    @Query("select (count(q) > 0) from Question q where q.deleted = false and q.subject.id = ?1")
    boolean existsBySubjectId(Integer id);
}