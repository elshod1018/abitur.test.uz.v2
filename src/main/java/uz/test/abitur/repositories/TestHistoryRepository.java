package uz.test.abitur.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import uz.test.abitur.domains.TestHistory;

public interface TestHistoryRepository extends JpaRepository<TestHistory, Integer> {
    @Query("select t from TestHistory t where t.testSessionId = ?1")
    TestHistory findByTestSessionId(Integer testSessionId);
}