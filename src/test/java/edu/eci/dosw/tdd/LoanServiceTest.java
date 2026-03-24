package edu.eci.dosw.tdd;

import edu.eci.dosw.tdd.core.exception.BookNotAvailableException;
import edu.eci.dosw.tdd.core.exception.LoanLimitExceededException;
import edu.eci.dosw.tdd.core.model.*;
import edu.eci.dosw.tdd.core.service.BookService;
import edu.eci.dosw.tdd.core.service.LoanService;
import edu.eci.dosw.tdd.core.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class LoanServiceTest {

    private LoanService loanService;
    private BookService bookService;
    private UserService userService;

    @BeforeEach
    void setUp() {
        bookService = new BookService();
        userService = new UserService();
        loanService = new LoanService(bookService, userService);

        userService.registerUser(new User("u1", "Juan"));

        bookService.addBook(new Book("b1", "Clean Code", "Martin", 0), 2);
        bookService.addBook(new Book("b2", "Clean Arch", "Martin", 0), 1);
        bookService.addBook(new Book("b3", "Pragmatic", "Hunt", 0), 1);
        bookService.addBook(new Book("b4", "Refactoring", "Fowler", 0), 1);
    }

    @Test
    void createLoan_shouldCreateSuccessfully() {
        Loan loan = loanService.createLoan("u1", "b1");

        assertNotNull(loan);
        assertEquals("u1", loan.getUser().getId());
        assertEquals("b1", loan.getBook().getId());
        assertEquals(LoanStatus.ACTIVE, loan.getStatus());
    }

    @Test
    void createLoan_shouldThrowException_whenBookNotAvailable() {
        bookService.updateAvailability("b1", false);

        assertThrows(BookNotAvailableException.class,
                () -> loanService.createLoan("u1", "b1"));
    }

    @Test
    void createLoan_shouldThrowException_whenLimitExceeded() {
        loanService.createLoan("u1", "b1");
        loanService.createLoan("u1", "b2");
        loanService.createLoan("u1", "b3");

        assertThrows(LoanLimitExceededException.class,
                () -> loanService.createLoan("u1", "b4"));
    }

    @Test
    void returnLoan_shouldReturnSuccessfully() {
        loanService.createLoan("u1", "b1");

        Loan loan = loanService.returnLoan("u1", "b1");

        assertEquals(LoanStatus.RETURNED, loan.getStatus());
        assertNotNull(loan.getReturnDate());
    }

    @Test
    void returnLoan_shouldThrowException_whenNoActiveLoan() {
        assertThrows(BookNotAvailableException.class,
                () -> loanService.returnLoan("u1", "b1"));
    }

    @Test
    void getLoansByUser_shouldReturnLoans() {
        loanService.createLoan("u1", "b1");
        loanService.createLoan("u1", "b2");

        List<Loan> loans = loanService.getLoansByUser("u1");

        assertEquals(2, loans.size());
    }

    @Test
    void getLoansByUser_shouldReturnEmpty_whenNoLoans() {
        List<Loan> loans = loanService.getLoansByUser("u1");

        assertTrue(loans.isEmpty());
    }
}