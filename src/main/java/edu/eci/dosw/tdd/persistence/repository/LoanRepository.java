package edu.eci.dosw.tdd.persistence.repository;

import edu.eci.dosw.tdd.persistence.dao.LoanEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface LoanRepository extends JpaRepository<LoanEntity, String> {

    List<LoanEntity> findByUserId(String userId);

    Optional<LoanEntity> findByUserIdAndBookIdAndStatus(String userId, String bookId, String status);

    long countByUserIdAndStatus(String userId, String status);
}