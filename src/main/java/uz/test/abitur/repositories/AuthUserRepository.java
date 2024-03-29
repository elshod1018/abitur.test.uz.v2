package uz.test.abitur.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import uz.test.abitur.domains.AuthUser;
import uz.test.abitur.enums.Status;

import java.util.List;
import java.util.Optional;

public interface AuthUserRepository extends JpaRepository<AuthUser, String> {
    @Transactional
    @Modifying
    @Query("update AuthUser a set a.status = ?1 where a.id = ?2")
    void updateStatusById(Status status, String id);

    @Query("""
            select (count(a) > 0) from AuthUser a
            where a.deleted = false and
            a.phoneNumber = ?1""")
    boolean exist(String phoneNumber);

    @Query("select a from AuthUser a where a.deleted = false and a.phoneNumber = ?1")
    Optional<AuthUser> findByPhoneNumber(String phoneNumber);

    @Query("select a from AuthUser a where a.deleted = false")
    Page<AuthUser> getAll(Pageable pageable);

    @Query("select a from AuthUser a where a.deleted = false and a.status = ?1")
    Page<AuthUser> getAllByStatus(Status status, Pageable pageable);
}