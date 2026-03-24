package edu.eci.dosw.tdd;

import edu.eci.dosw.tdd.core.exception.BookNotAvailableException;
import edu.eci.dosw.tdd.core.model.Book;
import edu.eci.dosw.tdd.core.service.BookService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class BookServiceTest {

    private BookService bookService;

    @BeforeEach
    void setUp() {
        bookService = new BookService();
    }

    @Test
    void addBook_shouldAddBookSuccessfully() {
        Book book = new Book("1", "Clean Code", "Robert Martin", 0);

        bookService.addBook(book, 3);

        Book result = bookService.getBookById("1");
        assertEquals("Clean Code", result.getTitle());
        assertEquals(3, result.getCopies());
        assertTrue(result.isAvailable());
    }

    @Test
    void getAllBooks_shouldReturnAllBooks() {
        bookService.addBook(new Book("1", "Clean Code", "Robert Martin", 0), 2);
        bookService.addBook(new Book("2", "Clean Architecture", "Robert Martin", 0), 1);

        List<Book> books = bookService.getAllBooks();
        assertEquals(2, books.size());
    }

    @Test
    void getBookById_shouldReturnBook_whenExists() {
        bookService.addBook(new Book("1", "Clean Code", "Robert Martin", 0), 2);

        Book result = bookService.getBookById("1");
        assertNotNull(result);
        assertEquals("1", result.getId());
    }

    @Test
    void getBookById_shouldThrowException_whenNotExists() {
        assertThrows(BookNotAvailableException.class,
                () -> bookService.getBookById("999"));
    }

    @Test
    void updateAvailability_shouldUpdateSuccessfully() {
        bookService.addBook(new Book("1", "Clean Code", "Robert Martin", 0), 2);

        Book result = bookService.updateAvailability("1", false);

        assertFalse(result.isAvailable());
    }

    @Test
    void decrementCopies_shouldMakeBookUnavailable_whenLastCopy() {
        bookService.addBook(new Book("1", "Clean Code", "Robert Martin", 0), 1);

        bookService.decrementCopies("1");

        assertFalse(bookService.getBookById("1").isAvailable());
        assertEquals(0, bookService.getBookById("1").getCopies());
    }

    @Test
    void decrementCopies_shouldThrowException_whenNoCopies() {
        bookService.addBook(new Book("1", "Clean Code", "Robert Martin", 0), 0);

        assertThrows(BookNotAvailableException.class,
                () -> bookService.decrementCopies("1"));
    }
}