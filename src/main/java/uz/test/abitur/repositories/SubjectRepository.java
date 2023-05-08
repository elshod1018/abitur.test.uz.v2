package uz.test.abitur.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.test.abitur.domains.Subject;

public interface SubjectRepository extends JpaRepository<Subject, Integer> {
}