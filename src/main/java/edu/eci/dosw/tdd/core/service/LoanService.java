package edu.eci.dosw.tdd.core.service;

import edu.eci.dosw.tdd.core.exception.BookNotAvailableException;
import edu.eci.dosw.tdd.core.exception.LoanLimitExceededException;
import edu.eci.dosw.tdd.core.model.Book;
import edu.eci.dosw.tdd.core.model.Loan;
import edu.eci.dosw.tdd.core.model.LoanStatus;
import edu.eci.dosw.tdd.core.model.User;
import edu.eci.dosw.tdd.persistence.repository.LoanRepositoryPort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class LoanService {

    private static final int MAX_LOANS = 3;

    private final LoanRepositoryPort loanRepository;
    private final BookService bookService;
    private final UserService userService;

    public LoanService(LoanRepositoryPort loanRepository,
                       BookService bookService,
                       UserService userService) {
        this.loanRepository = loanRepository;
        this.bookService = bookService;
        this.userService = userService;
    }

    public Loan createLoan(String userId, String bookId) {
        Book book = bookService.getBookById(bookId);

        if (!book.isAvailable())
            throw new BookNotAvailableException(bookId);

        long activeLoans = loanRepository.countByUserIdAndStatus(userId, "ACTIVE");
        if (activeLoans >= MAX_LOANS)
            throw new LoanLimitExceededException(userId);

        User user = userService.getUserById(userId);

        // Decrementar copias disponibles
        bookService.decrementCopies(bookId);
        // Recargar el book actualizado
        Book updatedBook = bookService.getBookById(bookId);

        Loan loan = new Loan(updatedBook, user);
        return loanRepository.save(loan);
    }

    public Loan returnLoan(String userId, String bookId) {
        Loan loan = loanRepository
                .findByUserIdAndBookIdAndStatus(userId, bookId, "ACTIVE")
                .orElseThrow(() -> new BookNotAvailableException(bookId));

        loan.returnBook();
        bookService.incrementCopies(bookId);

        return loanRepository.save(loan);
    }

    public List<Loan> getLoansByUser(String userId) {
        userService.getUserById(userId);
        return loanRepository.findByUserId(userId);
    }
}