package edu.eci.dosw.DOSW_Library;

import edu.eci.dosw.tdd.controller.dto.LoanDTO;
import edu.eci.dosw.tdd.controller.mapper.LoanMapper;
import edu.eci.dosw.tdd.core.model.Book;
import edu.eci.dosw.tdd.core.model.Loan;
import edu.eci.dosw.tdd.core.model.User;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LoanMapperTest {

    private final LoanMapper mapper = new LoanMapper();

    @Test
    void toDTO_shouldMapCorrectly() {
        Book book = new Book("b1", "Clean Code", "Robert Martin");
        User user = new User("u1", "Juan");
        Loan loan = new Loan(book, user);

        LoanDTO dto = mapper.toDTO(loan);

        assertEquals("u1", dto.getUserId());
        assertEquals("b1", dto.getBookId());
    }
}