package edu.eci.dosw.tdd.core.model;

import java.time.LocalDate;
import lombok.Data;

@Data
public class Loan {
    private Book book;
    private User user;
    private LocalDate loanDate;
    private LocalDate returnDate;
    private LoanStatus status;

    public Loan(Book book, User user) {
        this.book = book;
        this.user = user;
        this.loanDate = LocalDate.now();
        this.status = LoanStatus.ACTIVE;
    }

    public void returnBook() {
        this.status = LoanStatus.RETURNED;
        this.returnDate = LocalDate.now();
    }
}