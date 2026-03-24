package edu.eci.dosw.tdd;

import edu.eci.dosw.tdd.core.model.Book;
import edu.eci.dosw.tdd.core.model.Loan;
import edu.eci.dosw.tdd.core.model.User;
import edu.eci.dosw.tdd.core.validator.LoanValidator;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LoanValidatorTest {

    private final LoanValidator validator = new LoanValidator();

    @Test
    void validate_shouldPass_whenLoanIsValid() {
        Book book = new Book("b1", "Clean Code", "Martin", 2);
        User user = new User("u1", "Juan");

        Loan loan = new Loan(book, user);

        assertDoesNotThrow(() -> validator.validate(loan));
    }
}