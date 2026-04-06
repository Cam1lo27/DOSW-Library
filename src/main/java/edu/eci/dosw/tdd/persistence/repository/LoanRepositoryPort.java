package edu.eci.dosw.tdd.persistence.repository;

import edu.eci.dosw.tdd.core.model.Loan;
import java.util.List;
import java.util.Optional;

public interface LoanRepositoryPort {
    Loan save(Loan loan);
    Optional<Loan> findByUserIdAndBookIdAndStatus(String userId, String bookId, String status);
    List<Loan> findByUserId(String userId);
    long countByUserIdAndStatus(String userId, String status);
}