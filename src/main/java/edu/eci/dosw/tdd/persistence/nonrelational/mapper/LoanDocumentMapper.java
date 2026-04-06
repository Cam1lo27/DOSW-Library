package edu.eci.dosw.tdd.persistence.nonrelational.mapper;

import edu.eci.dosw.tdd.core.model.Loan;
import edu.eci.dosw.tdd.core.model.LoanStatus;
import edu.eci.dosw.tdd.persistence.nonrelational.document.LoanDocument;
import org.springframework.stereotype.Component;
import java.util.ArrayList;
import java.util.stream.Collectors;

@Component
public class LoanDocumentMapper {

    public LoanDocument toDocument(Loan loan) {
        LoanDocument doc = new LoanDocument();
        doc.setId(loan.getId());
        doc.setBookId(loan.getBook() != null ? loan.getBook().getId() : null);
        doc.setUserId(loan.getUser() != null ? loan.getUser().getId() : null);
        doc.setLoanDate(loan.getLoanDate());
        doc.setReturnDate(loan.getReturnDate());
        doc.setStatus(loan.getStatus() != null ? loan.getStatus().name() : null);
        if (loan.getLoanHistory() != null) {
            doc.setLoanHistory(loan.getLoanHistory().stream()
                    .map(h -> new LoanDocument.LoanHistoryEntry(h.getStatus().name(), h.getDate()))
                    .collect(Collectors.toList()));
        } else {
            doc.setLoanHistory(new ArrayList<>());
        }
        return doc;
    }

    public Loan toDomain(LoanDocument doc, Loan loan) {
        loan.setId(doc.getId());
        loan.setLoanDate(doc.getLoanDate());
        loan.setReturnDate(doc.getReturnDate());
        loan.setStatus(doc.getStatus() != null ? LoanStatus.valueOf(doc.getStatus()) : null);
        if (doc.getLoanHistory() != null) {
            loan.setLoanHistory(doc.getLoanHistory().stream()
                    .map(h -> new Loan.LoanHistoryEntry(LoanStatus.valueOf(h.getStatus()), h.getDate()))
                    .collect(Collectors.toList()));
        }
        return loan;
    }
}