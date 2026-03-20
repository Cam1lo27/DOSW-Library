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
        Loan loan = new Loan(new Book("b1", "Clean Code", "Robert Martin"), new User("u1", "Juan"));
        assertDoesNotThrow(() -> validator.validate(loan));
    }
}