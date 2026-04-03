package edu.eci.dosw.tdd.core.service;

import edu.eci.dosw.tdd.core.exception.BookNotAvailableException;
import edu.eci.dosw.tdd.core.exception.LoanLimitExceededException;
import edu.eci.dosw.tdd.core.model.Loan;
import edu.eci.dosw.tdd.persistence.dao.LoanEntity;
import edu.eci.dosw.tdd.persistence.mapper.LoanPersistenceMapper;
import edu.eci.dosw.tdd.persistence.repository.LoanRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class LoanService {

    private static final int MAX_LOANS = 3;

    private final LoanRepository loanRepository;
    private final LoanPersistenceMapper loanMapper;
    private final BookService bookService;
    private final UserService userService;

    public LoanService(LoanRepository loanRepository, LoanPersistenceMapper loanMapper,
                       BookService bookService, UserService userService) {
        this.loanRepository = loanRepository;
        this.loanMapper = loanMapper;
        this.bookService = bookService;
        this.userService = userService;
    }

    public Loan createLoan(String userId, String bookId) {
        var book = bookService.getBookById(bookId);

        if (!book.isAvailable()) throw new BookNotAvailableException(bookId);

        long activeLoans = loanRepository.countByUserIdAndStatus(userId, "ACTIVE");
        if (activeLoans >= MAX_LOANS) throw new LoanLimitExceededException(userId);

        bookService.decrementCopies(bookId);

        LoanEntity entity = new LoanEntity();
        entity.setUser(userService.getEntityById(userId));
        entity.setBook(bookService.getEntityById(bookId));
        entity.setLoanDate(LocalDate.now());
        entity.setStatus("ACTIVE");

        return loanMapper.toModel(loanRepository.save(entity));
    }

    public Loan returnLoan(String userId, String bookId) {
        LoanEntity entity = loanRepository
                .findByUserIdAndBookIdAndStatus(userId, bookId, "ACTIVE")
                .orElseThrow(() -> new BookNotAvailableException(bookId));

        entity.setStatus("RETURNED");
        entity.setReturnDate(LocalDate.now());
        bookService.incrementCopies(bookId);

        return loanMapper.toModel(loanRepository.save(entity));
    }

    public List<Loan> getLoansByUser(String userId) {
        userService.getUserById(userId);
        return loanRepository.findByUserId(userId).stream()
                .map(loanMapper::toModel)
                .collect(Collectors.toList());
    }
}