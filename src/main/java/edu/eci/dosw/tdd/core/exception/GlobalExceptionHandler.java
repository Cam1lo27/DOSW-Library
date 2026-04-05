package edu.eci.dosw.tdd.core.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BookNotAvailableException.class)
    public ResponseEntity<Map<String, String>> handleBookNotAvailable(BookNotAvailableException ex) {
        return buildResponse(HttpStatus.CONFLICT, ex.getMessage());
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<Map<String, String>> handleUserNotFound(UserNotFoundException ex) {
        return buildResponse(HttpStatus.NOT_FOUND, ex.getMessage());
    }

    @ExceptionHandler(LoanLimitExceededException.class)
    public ResponseEntity<Map<String, String>> handleLoanLimit(LoanLimitExceededException ex) {
        return buildResponse(HttpStatus.BAD_REQUEST, ex.getMessage());
    }

    private ResponseEntity<Map<String, String>> buildResponse(HttpStatus status, String message) {
        Map<String, String> body = new HashMap<>();
        body.put("error", message);
        return ResponseEntity.status(status).body(body);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<Map<String, String>> handleForbidden(AccessDeniedException ex) {
        return buildResponse(HttpStatus.FORBIDDEN, "No tienes permisos para esta operación.");
    }

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<Map<String, String>> handleUnauthorized(AuthenticationException ex) {
        return buildResponse(HttpStatus.UNAUTHORIZED, "No autenticado o token inválido.");
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<Map<String, String>> handleRuntime(RuntimeException ex) {
        return buildResponse(HttpStatus.UNAUTHORIZED, ex.getMessage());
    }
}