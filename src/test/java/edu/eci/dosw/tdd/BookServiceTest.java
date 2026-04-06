package edu.eci.dosw.tdd;

import edu.eci.dosw.tdd.core.exception.BookNotAvailableException;
import edu.eci.dosw.tdd.core.model.Book;
import edu.eci.dosw.tdd.core.service.BookService;
import edu.eci.dosw.tdd.persistence.repository.BookRepositoryPort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BookServiceTest {

    @Mock
    private BookRepositoryPort bookRepository;

    @InjectMocks
    private BookService bookService;

    private Book book;

    @BeforeEach
    void setUp() {
        book = new Book();
        book.setId("b1");
        book.setTitle("Clean Code");
        book.setAuthor("Robert Martin");
        book.setTotalCopies(5);
        book.setAvailableCopies(5);
    }

    @Test
    void addBook_shouldSaveAndReturnBook() {
        when(bookRepository.save(any())).thenReturn(book);

        Book result = bookService.addBook(book);

        assertNotNull(result);
        assertEquals("b1", result.getId());
        verify(bookRepository).save(any());
    }

    @Test
    void addBook_shouldThrowWhenCopiesIsZero() {
        book.setTotalCopies(0);
        assertThrows(IllegalArgumentException.class, () -> bookService.addBook(book));
    }

    @Test
    void getBookById_shouldReturnBook() {
        when(bookRepository.findById("b1")).thenReturn(Optional.of(book));

        Book result = bookService.getBookById("b1");
        assertEquals("b1", result.getId());
    }

    @Test
    void getBookById_shouldThrowWhenNotFound() {
        when(bookRepository.findById("x")).thenReturn(Optional.empty());
        assertThrows(BookNotAvailableException.class, () -> bookService.getBookById("x"));
    }

    @Test
    void getAllBooks_shouldReturnList() {
        when(bookRepository.findAll()).thenReturn(List.of(book));

        List<Book> result = bookService.getAllBooks();
        assertEquals(1, result.size());
    }

    @Test
    void decrementCopies_shouldReduceAvailableCopies() {
        Book updated = new Book();
        updated.setId("b1");
        updated.setTotalCopies(5);
        updated.setAvailableCopies(4);

        when(bookRepository.findById("b1")).thenReturn(Optional.of(book));
        when(bookRepository.save(any())).thenReturn(updated);

        Book result = bookService.decrementCopies("b1");

        assertEquals(4, result.getAvailableCopies());
        verify(bookRepository).save(any());
    }

    @Test
    void decrementCopies_shouldThrowWhenNoCopiesAvailable() {
        book.setAvailableCopies(0);
        when(bookRepository.findById("b1")).thenReturn(Optional.of(book));

        assertThrows(BookNotAvailableException.class, () -> bookService.decrementCopies("b1"));
    }

    @Test
    void incrementCopies_shouldIncreaseAvailableCopies() {
        book.setAvailableCopies(3);

        Book updated = new Book();
        updated.setId("b1");
        updated.setTotalCopies(5);
        updated.setAvailableCopies(4);

        when(bookRepository.findById("b1")).thenReturn(Optional.of(book));
        when(bookRepository.save(any())).thenReturn(updated);

        Book result = bookService.incrementCopies("b1");

        assertEquals(4, result.getAvailableCopies());
        verify(bookRepository).save(any());
    }

    @Test
    void incrementCopies_shouldThrowWhenAlreadyAtMax() {
        book.setAvailableCopies(5);
        when(bookRepository.findById("b1")).thenReturn(Optional.of(book));

        assertThrows(IllegalStateException.class, () -> bookService.incrementCopies("b1"));
    }
}