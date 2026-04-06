package edu.eci.dosw.tdd.core.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Loan {
    private String id;
    private Book book;
    private User user;
    private LocalDate loanDate;
    private LocalDate returnDate;
    private LoanStatus status;
    private List<LoanHistoryEntry> loanHistory = new ArrayList<>();

    public Loan(Book book, User user) {
        this.book = book;
        this.user = user;
        this.loanDate = LocalDate.now();
        this.status = LoanStatus.ACTIVE;
        this.loanHistory = new ArrayList<>();
        this.loanHistory.add(new LoanHistoryEntry(LoanStatus.ACTIVE, LocalDate.now()));
    }

    public void returnBook() {
        this.status = LoanStatus.RETURNED;
        this.returnDate = LocalDate.now();
        this.loanHistory.add(new LoanHistoryEntry(LoanStatus.RETURNED, LocalDate.now()));
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class LoanHistoryEntry {
        private LoanStatus status;
        private LocalDate date;
    }
}