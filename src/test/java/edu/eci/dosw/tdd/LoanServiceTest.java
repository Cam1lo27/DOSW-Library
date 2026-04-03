package edu.eci.dosw.tdd;

import edu.eci.dosw.tdd.core.exception.BookNotAvailableException;
import edu.eci.dosw.tdd.core.exception.LoanLimitExceededException;
import edu.eci.dosw.tdd.core.model.Book;
import edu.eci.dosw.tdd.core.model.Loan;
import edu.eci.dosw.tdd.core.model.LoanStatus;
import edu.eci.dosw.tdd.core.model.User;
import edu.eci.dosw.tdd.core.service.BookService;
import edu.eci.dosw.tdd.core.service.LoanService;
import edu.eci.dosw.tdd.core.service.UserService;
import edu.eci.dosw.tdd.persistence.dao.BookEntity;
import edu.eci.dosw.tdd.persistence.dao.LoanEntity;
import edu.eci.dosw.tdd.persistence.dao.UserEntity;
import edu.eci.dosw.tdd.persistence.mapper.LoanPersistenceMapper;
import edu.eci.dosw.tdd.persistence.repository.LoanRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class LoanServiceTest {

    @Mock
    private LoanRepository loanRepository;

    @Mock
    private LoanPersistenceMapper loanMapper;

    @Mock
    private BookService bookService;

    @Mock
    private UserService userService;

    @InjectMocks
    private LoanService loanService;

    private UserEntity userEntity;
    private BookEntity bookEntity;
    private LoanEntity loanEntity;
    private Book book;
    private User user;
    private Loan loan;

    @BeforeEach
    void setUp() {
        userEntity = new UserEntity("u1", "jdoe", "hashed123", "John Doe", "USER");
        bookEntity = new BookEntity("b1", "Clean Code", "Robert Martin", 5, 5);
        book = new Book("b1", "Clean Code", "Robert Martin", 5, 5);
        user = new User("u1", "jdoe", "hashed123", "John Doe", "USER");

        loanEntity = new LoanEntity();
        loanEntity.setUser(userEntity);
        loanEntity.setBook(bookEntity);
        loanEntity.setLoanDate(LocalDate.now());
        loanEntity.setStatus("ACTIVE");

        loan = new Loan();
        loan.setBook(book);
        loan.setUser(user);
        loan.setLoanDate(LocalDate.now());
        loan.setStatus(LoanStatus.ACTIVE);
    }

    @Test
    void createLoan_shouldCreateSuccessfully() {
        when(bookService.getBookById("b1")).thenReturn(book);
        when(loanRepository.countByUserIdAndStatus("u1", "ACTIVE")).thenReturn(0L);
        when(userService.getEntityById("u1")).thenReturn(userEntity);
        when(bookService.getEntityById("b1")).thenReturn(bookEntity);
        when(loanRepository.save(any())).thenReturn(loanEntity);
        when(loanMapper.toModel(loanEntity)).thenReturn(loan);

        Loan result = loanService.createLoan("u1", "b1");

        assertNotNull(result);
        assertEquals(LoanStatus.ACTIVE, result.getStatus());
        verify(bookService).decrementCopies("b1");
    }

    @Test
    void createLoan_shouldThrowWhenBookNotAvailable() {
        book.setAvailableCopies(0);
        when(bookService.getBookById("b1")).thenReturn(book);

        assertThrows(BookNotAvailableException.class, () -> loanService.createLoan("u1", "b1"));
        verify(bookService, never()).decrementCopies(any());
    }

    @Test
    void createLoan_shouldThrowWhenLoanLimitExceeded() {
        when(bookService.getBookById("b1")).thenReturn(book);
        when(loanRepository.countByUserIdAndStatus("u1", "ACTIVE")).thenReturn(3L);

        assertThrows(LoanLimitExceededException.class, () -> loanService.createLoan("u1", "b1"));
        verify(bookService, never()).decrementCopies(any());
    }

    @Test
    void returnLoan_shouldReturnSuccessfully() {
        when(loanRepository.findByUserIdAndBookIdAndStatus("u1", "b1", "ACTIVE"))
                .thenReturn(Optional.of(loanEntity));
        when(loanRepository.save(any())).thenReturn(loanEntity);
        when(loanMapper.toModel(loanEntity)).thenReturn(loan);

        Loan result = loanService.returnLoan("u1", "b1");

        assertNotNull(result);
        assertEquals("RETURNED", loanEntity.getStatus());
        verify(bookService).incrementCopies("b1");
    }

    @Test
    void returnLoan_shouldThrowWhenNoActiveLoanFound() {
        when(loanRepository.findByUserIdAndBookIdAndStatus("u1", "b1", "ACTIVE"))
                .thenReturn(Optional.empty());

        assertThrows(BookNotAvailableException.class, () -> loanService.returnLoan("u1", "b1"));
        verify(bookService, never()).incrementCopies(any());
    }

    @Test
    void getLoansByUser_shouldReturnUserLoans() {
        when(loanRepository.findByUserId("u1")).thenReturn(List.of(loanEntity));
        when(loanMapper.toModel(loanEntity)).thenReturn(loan);

        List<Loan> result = loanService.getLoansByUser("u1");
        assertEquals(1, result.size());
    }
}