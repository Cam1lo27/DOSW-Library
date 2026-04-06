package edu.eci.dosw.tdd.persistence.nonrelational.document;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "loans")
public class LoanDocument {

    @Id
    private String id;
    private String bookId;
    private String userId;
    private LocalDate loanDate;
    private LocalDate returnDate;
    private String status;
    private List<LoanHistoryEntry> loanHistory;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class LoanHistoryEntry {
        private String status;
        private LocalDate date;
    }
}