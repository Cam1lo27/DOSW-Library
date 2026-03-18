package edu.eci.dosw.tdd.core.validator;

import edu.eci.dosw.tdd.core.model.Loan;
import org.springframework.stereotype.Component;

@Component
public class LoanValidator {

    public void validate(Loan loan) {
        if (loan.getBook() == null)
            throw new IllegalArgumentException("El préstamo debe tener un libro.");
        if (loan.getUser() == null)
            throw new IllegalArgumentException("El préstamo debe tener un usuario.");
    }
}