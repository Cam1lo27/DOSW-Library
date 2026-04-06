package edu.eci.dosw.tdd.persistence.relational;

import edu.eci.dosw.tdd.core.model.Loan;
import edu.eci.dosw.tdd.persistence.dao.BookEntity;
import edu.eci.dosw.tdd.persistence.dao.LoanEntity;
import edu.eci.dosw.tdd.persistence.dao.UserEntity;
import edu.eci.dosw.tdd.persistence.mapper.LoanPersistenceMapper;
import edu.eci.dosw.tdd.persistence.repository.LoanRepositoryPort;
import edu.eci.dosw.tdd.persistence.repository.jpa.BookJpaRepository;
import edu.eci.dosw.tdd.persistence.repository.jpa.LoanJpaRepository;
import edu.eci.dosw.tdd.persistence.repository.jpa.UserJpaRepository;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
@Profile("relational")
public class LoanRepositoryJpaImpl implements LoanRepositoryPort {

    private final LoanJpaRepository repository;
    private final LoanPersistenceMapper mapper;
    private final BookJpaRepository bookJpaRepository;
    private final UserJpaRepository userJpaRepository;

    public LoanRepositoryJpaImpl(LoanJpaRepository repository,
                                 LoanPersistenceMapper mapper,
                                 BookJpaRepository bookJpaRepository,
                                 UserJpaRepository userJpaRepository) {
        this.repository = repository;
        this.mapper = mapper;
        this.bookJpaRepository = bookJpaRepository;
        this.userJpaRepository = userJpaRepository;
    }

    @Override
    public Loan save(Loan loan) {
        LoanEntity entity;

        // Si el loan ya tiene id (actualización), buscar la entidad existente
        if (loan.getId() != null) {
            entity = repository.findById(loan.getId()).orElse(new LoanEntity());
        } else {
            entity = new LoanEntity();
        }

        if (loan.getBook() != null) {
            BookEntity bookEntity = bookJpaRepository.findById(loan.getBook().getId())
                    .orElseThrow(() -> new IllegalArgumentException("Book not found: " + loan.getBook().getId()));
            entity.setBook(bookEntity);
        }

        if (loan.getUser() != null) {
            UserEntity userEntity = userJpaRepository.findById(loan.getUser().getId())
                    .orElseThrow(() -> new IllegalArgumentException("User not found: " + loan.getUser().getId()));
            entity.setUser(userEntity);
        }

        entity.setLoanDate(loan.getLoanDate());
        entity.setReturnDate(loan.getReturnDate());
        entity.setStatus(loan.getStatus() != null ? loan.getStatus().name() : "ACTIVE");

        LoanEntity saved = repository.save(entity);
        return mapper.toModel(saved);
    }

    @Override
    public Optional<Loan> findByUserIdAndBookIdAndStatus(String userId, String bookId, String status) {
        return repository.findByUserIdAndBookIdAndStatus(userId, bookId, status)
                .map(mapper::toModel);
    }

    @Override
    public List<Loan> findByUserId(String userId) {
        return repository.findByUserId(userId).stream()
                .map(mapper::toModel)
                .collect(Collectors.toList());
    }

    @Override
    public long countByUserIdAndStatus(String userId, String status) {
        return repository.countByUserIdAndStatus(userId, status);
    }
}