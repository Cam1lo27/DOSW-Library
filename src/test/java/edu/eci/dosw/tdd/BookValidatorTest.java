package edu.eci.dosw.tdd;

import edu.eci.dosw.tdd.core.model.Book;
import edu.eci.dosw.tdd.core.validator.BookValidator;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BookValidatorTest {

    private final BookValidator validator = new BookValidator();

    @Test
    void validate_shouldPass_whenBookIsValid() {
        Book book = new Book("1", "Clean Code", "Robert Martin", 5);

        assertDoesNotThrow(() -> validator.validate(book));
    }

    @Test
    void validate_shouldThrow_whenTitleIsBlank() {
        Book book = new Book("1", "", "Robert Martin", 5);

        assertThrows(IllegalArgumentException.class,
                () -> validator.validate(book));
    }

    @Test
    void validate_shouldThrow_whenAuthorIsBlank() {
        Book book = new Book("1", "Clean Code", "", 5);

        assertThrows(IllegalArgumentException.class,
                () -> validator.validate(book));
    }

    @Test
    void validate_shouldThrow_whenIdIsNull() {
        Book book = new Book(null, "Clean Code", "Robert Martin", 5);

        assertThrows(IllegalArgumentException.class,
                () -> validator.validate(book));
    }

    @Test
    void validate_shouldThrow_whenCopiesNegative() {
        Book book = new Book("1", "Clean Code", "Robert Martin", -1);

        assertThrows(IllegalArgumentException.class,
                () -> validator.validate(book));
    }
}