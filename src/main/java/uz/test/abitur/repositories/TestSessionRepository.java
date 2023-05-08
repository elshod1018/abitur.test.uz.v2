package uz.test.abitur.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.test.abitur.domains.TestSession;

public interface TestSessionRepository extends JpaRepository<TestSession, Integer> {
}