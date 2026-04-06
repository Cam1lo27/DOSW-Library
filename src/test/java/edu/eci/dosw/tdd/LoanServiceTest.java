package edu.eci.dosw.tdd;

import edu.eci.dosw.tdd.core.model.*;
import edu.eci.dosw.tdd.core.service.BookService;
import edu.eci.dosw.tdd.core.service.LoanService;
import edu.eci.dosw.tdd.core.service.UserService;
import edu.eci.dosw.tdd.persistence.repository.BookRepositoryPort;
import edu.eci.dosw.tdd.persistence.repository.LoanRepositoryPort;
import edu.eci.dosw.tdd.persistence.repository.UserRepositoryPort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class LoanServiceTest {

    @Mock private LoanRepositoryPort loanRepository;
    @Mock private BookRepositoryPort bookRepository;
    @Mock private UserRepositoryPort userRepository;
    @Mock private PasswordEncoder passwordEncoder;

    private LoanService loanService;
    private BookService bookService;
    private UserService userService;

    private User testUser;
    private Book testBook;
    private Loan testLoan;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        userService = new UserService(userRepository, passwordEncoder);
        bookService = new BookService(bookRepository);
        loanService = new LoanService(loanRepository, bookService, userService);

        testUser = new User();
        testUser.setId("user-1");
        testUser.setUsername("testuser");
        testUser.setPasswordHash("hash");
        testUser.setName("Test User");
        testUser.setRole("USER");

        testBook = new Book();
        testBook.setId("book-1");
        testBook.setTitle("Clean Code");
        testBook.setAuthor("Robert Martin");
        testBook.setTotalCopies(3);
        testBook.setAvailableCopies(2); // 2/3: ya hay 1 prestamo activo, permite incrementar al devolver

        testLoan = new Loan(testBook, testUser);
        testLoan.setId("loan-1");
    }

    @Test
    void dadoQueExisteUnPrestamo_cuandoLoConsulto_entoncesLaConsultaEsExitosaValidandoElId() {
        when(userRepository.findById("user-1")).thenReturn(Optional.of(testUser));
        when(loanRepository.findByUserId("user-1")).thenReturn(List.of(testLoan));

        List<Loan> loans = loanService.getLoansByUser("user-1");

        assertFalse(loans.isEmpty());
        assertEquals("loan-1", loans.get(0).getId());
    }

    @Test
    void dadoQueNoHayPrestamos_cuandoLoConsulto_entoncesNoRetornaNingunResultado() {
        when(userRepository.findById("user-1")).thenReturn(Optional.of(testUser));
        when(loanRepository.findByUserId("user-1")).thenReturn(new ArrayList<>());

        List<Loan> loans = loanService.getLoansByUser("user-1");

        assertTrue(loans.isEmpty());
    }

    @Test
    void dadoQueNoHayPrestamos_cuandoCreoUno_entoncesLaCreacionEsExitosa() {
        Book bookWithCopies = new Book();
        bookWithCopies.setId("book-1");
        bookWithCopies.setTotalCopies(3);
        bookWithCopies.setAvailableCopies(3);

        Book bookDecremented = new Book();
        bookDecremented.setId("book-1");
        bookDecremented.setTotalCopies(3);
        bookDecremented.setAvailableCopies(2);

        when(bookRepository.findById("book-1"))
                .thenReturn(Optional.of(bookWithCopies))   // createLoan: getBookById
                .thenReturn(Optional.of(bookWithCopies))   // decrementCopies: getBookById
                .thenReturn(Optional.of(bookDecremented)); // createLoan: getBookById actualizado
        when(bookRepository.save(any(Book.class))).thenReturn(bookDecremented);
        when(userRepository.findById("user-1")).thenReturn(Optional.of(testUser));
        when(loanRepository.countByUserIdAndStatus("user-1", "ACTIVE")).thenReturn(0L);
        when(loanRepository.save(any(Loan.class))).thenReturn(testLoan);

        Loan loan = loanService.createLoan("user-1", "book-1");

        assertNotNull(loan);
        assertEquals("book-1", loan.getBook().getId());
        assertEquals("user-1", loan.getUser().getId());
    }

    @Test
    void dadoQueExisteUnPrestamo_cuandoLoElimino_entoncesLaEliminacionEsExitosa() {
        testLoan.setStatus(LoanStatus.ACTIVE);

        Book bookIncremented = new Book();
        bookIncremented.setId("book-1");
        bookIncremented.setTotalCopies(3);
        bookIncremented.setAvailableCopies(3);

        when(loanRepository.findByUserIdAndBookIdAndStatus("user-1", "book-1", "ACTIVE"))
                .thenReturn(Optional.of(testLoan));
        when(bookRepository.findById("book-1")).thenReturn(Optional.of(testBook)); // availableCopies=2 < totalCopies=3
        when(bookRepository.save(any(Book.class))).thenReturn(bookIncremented);
        when(loanRepository.save(any(Loan.class))).thenAnswer(inv -> inv.getArgument(0));

        Loan returned = loanService.returnLoan("user-1", "book-1");

        assertEquals(LoanStatus.RETURNED, returned.getStatus());
    }

    @Test
    void dadoQueExisteUnPrestamo_cuandoLoEliminoYConsulto_entoncesNoRetornaNingunResultado() {
        testLoan.setStatus(LoanStatus.RETURNED);
        when(userRepository.findById("user-1")).thenReturn(Optional.of(testUser));
        when(loanRepository.findByUserId("user-1")).thenReturn(List.of(testLoan));

        List<Loan> loans = loanService.getLoansByUser("user-1");
        List<Loan> activeLoans = loans.stream()
                .filter(l -> l.getStatus() == LoanStatus.ACTIVE)
                .toList();

        assertTrue(activeLoans.isEmpty());
    }
}