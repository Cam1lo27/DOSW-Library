package edu.eci.dosw.tdd;

import edu.eci.dosw.tdd.core.exception.BookNotAvailableException;
import edu.eci.dosw.tdd.core.exception.GlobalExceptionHandler;
import edu.eci.dosw.tdd.core.exception.LoanLimitExceededException;
import edu.eci.dosw.tdd.core.exception.UserNotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class GlobalExceptionHandlerTest {

    private final GlobalExceptionHandler handler = new GlobalExceptionHandler();

    @Test
    void handleBookNotAvailable_shouldReturnConflict() {
        BookNotAvailableException ex = new BookNotAvailableException("b1");
        ResponseEntity<Map<String, String>> response = handler.handleBookNotAvailable(ex);

        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
        assertTrue(response.getBody().containsKey("error"));
    }

    @Test
    void handleUserNotFound_shouldReturnNotFound() {
        UserNotFoundException ex = new UserNotFoundException("u1");
        ResponseEntity<Map<String, String>> response = handler.handleUserNotFound(ex);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertTrue(response.getBody().containsKey("error"));
    }

    @Test
    void handleLoanLimit_shouldReturnBadRequest() {
        LoanLimitExceededException ex = new LoanLimitExceededException("u1");
        ResponseEntity<Map<String, String>> response = handler.handleLoanLimit(ex);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertTrue(response.getBody().containsKey("error"));
    }
}