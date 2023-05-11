package uz.test.abitur.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import uz.test.abitur.domains.Subject;

import java.util.List;
import java.util.Optional;

public interface SubjectRepository extends JpaRepository<Subject, Integer> {
    @Query("select s from Subject s where s.deleted = false and s.mandatory = true")
    List<Subject> findMandatorySubjects();
    @Query("select s from Subject s where s.deleted = false  and s.id = ?1")
    Optional<Subject> findSubjectById(Integer id);
    @Query("select s from Subject s where s.deleted = false  and s.code = ?1 and s.mandatory = ?2")
    Optional<Subject> findByCodeAndMandatory(String code, boolean mandatory);
    @Query("select s from Subject s where s.deleted = false")
    Page<Subject> getAll(Pageable pageable);

}