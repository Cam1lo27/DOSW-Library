package edu.eci.dosw.tdd.core.service;

import edu.eci.dosw.tdd.core.exception.BookNotAvailableException;
import edu.eci.dosw.tdd.core.exception.LoanLimitExceededException;
import edu.eci.dosw.tdd.core.model.Loan;
import edu.eci.dosw.tdd.core.model.LoanStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class LoanService {

    private static final int MAX_LOANS = 3;
    private final List<Loan> loans = new ArrayList<>();
    private final BookService bookService;
    private final UserService userService;

    public LoanService(BookService bookService, UserService userService) {
        this.bookService = bookService;
        this.userService = userService;
    }

    public Loan createLoan(String userId, String bookId) {
        var user = userService.getUserById(userId);
        var book = bookService.getBookById(bookId);

        if (!book.isAvailable()) throw new BookNotAvailableException(bookId);

        long activeLoans = loans.stream()
                .filter(l -> l.getUser().getId().equals(userId))
                .filter(l -> l.getStatus() == LoanStatus.ACTIVE)
                .count();

        if (activeLoans >= MAX_LOANS) throw new LoanLimitExceededException(userId);

        bookService.decrementCopies(bookId);
        Loan loan = new Loan(book, user);
        loans.add(loan);
        return loan;
    }

    public Loan returnLoan(String userId, String bookId) {
        Loan loan = loans.stream()
                .filter(l -> l.getUser().getId().equals(userId))
                .filter(l -> l.getBook().getId().equals(bookId))
                .filter(l -> l.getStatus() == LoanStatus.ACTIVE)
                .findFirst()
                .orElseThrow(() -> new BookNotAvailableException(bookId));

        loan.returnBook();
        bookService.incrementCopies(bookId);
        return loan;
    }

    public List<Loan> getLoansByUser(String userId) {
        userService.getUserById(userId);
        return loans.stream()
                .filter(l -> l.getUser().getId().equals(userId))
                .collect(Collectors.toList());
    }
}