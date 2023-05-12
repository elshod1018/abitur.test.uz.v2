package uz.test.abitur.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import uz.test.abitur.domains.TestSession;

import java.util.Optional;

public interface TestSessionRepository extends JpaRepository<TestSession, Integer> {
    @Query("select t from TestSession t where t.finished = false and t.finishedAt > NOW() and t.userId = ?1")
    TestSession findByUserId(String userId);

    @Query("select t from TestSession t where t.id = ?1")
    Optional<TestSession> findTestSessionById(Integer id);
}