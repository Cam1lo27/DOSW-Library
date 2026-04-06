package edu.eci.dosw.tdd.persistence.nonrelational;

import edu.eci.dosw.tdd.core.model.Loan;
import edu.eci.dosw.tdd.core.model.LoanStatus;
import edu.eci.dosw.tdd.persistence.nonrelational.document.LoanDocument;
import edu.eci.dosw.tdd.persistence.nonrelational.mapper.LoanDocumentMapper;
import edu.eci.dosw.tdd.persistence.nonrelational.repository.LoanMongoRepository;
import edu.eci.dosw.tdd.persistence.repository.LoanRepositoryPort;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
@Profile("mongo")
public class LoanRepositoryMongoImpl implements LoanRepositoryPort {

    private final LoanMongoRepository repository;
    private final LoanDocumentMapper mapper;

    public LoanRepositoryMongoImpl(LoanMongoRepository repository, LoanDocumentMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public Loan save(Loan loan) {
        LoanDocument doc = mapper.toDocument(loan);
        LoanDocument saved = repository.save(doc);
        return mapper.toDomain(saved, loan);
    }

    @Override
    public Optional<Loan> findByUserIdAndBookIdAndStatus(String userId, String bookId, String status) {
        return repository.findByUserIdAndBookIdAndStatus(userId, bookId, status)
                .map(doc -> mapper.toDomain(doc, new Loan()));
    }

    @Override
    public List<Loan> findByUserId(String userId) {
        return repository.findByUserId(userId).stream()
                .map(doc -> mapper.toDomain(doc, new Loan()))
                .collect(Collectors.toList());
    }

    @Override
    public long countByUserIdAndStatus(String userId, String status) {
        return repository.countByUserIdAndStatus(userId, status);
    }
}