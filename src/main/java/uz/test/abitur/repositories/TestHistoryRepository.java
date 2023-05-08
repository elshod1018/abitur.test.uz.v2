package uz.test.abitur.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.test.abitur.domains.TestHistory;

public interface TestHistoryRepository extends JpaRepository<TestHistory, Integer> {
}