package edu.eci.dosw.tdd.persistence.nonrelational.repository;

import edu.eci.dosw.tdd.persistence.nonrelational.document.LoanDocument;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.List;
import java.util.Optional;

public interface LoanMongoRepository extends MongoRepository<LoanDocument, String> {
    List<LoanDocument> findByUserId(String userId);
    Optional<LoanDocument> findByUserIdAndBookIdAndStatus(String userId, String bookId, String status);
    long countByUserIdAndStatus(String userId, String status);
}