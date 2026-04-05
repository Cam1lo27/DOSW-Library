package edu.eci.dosw.tdd;

import edu.eci.dosw.tdd.core.exception.BookNotAvailableException;
import edu.eci.dosw.tdd.core.model.Book;
import edu.eci.dosw.tdd.core.service.BookService;
import edu.eci.dosw.tdd.persistence.dao.BookEntity;
import edu.eci.dosw.tdd.persistence.mapper.BookPersistenceMapper;
import edu.eci.dosw.tdd.persistence.repository.BookRepository;
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
    private BookRepository bookRepository;

    @Mock
    private BookPersistenceMapper bookMapper;

    @InjectMocks
    private BookService bookService;

    private BookEntity bookEntity;
    private Book book;

    @BeforeEach
    void setUp() {
        bookEntity = new BookEntity("b1", "Clean Code", "Robert Martin", 5, 5);
        book = new Book("b1", "Clean Code", "Robert Martin", 5, 5);
    }

    @Test
    void addBook_shouldSaveAndReturnBook() {
        when(bookMapper.toEntity(any())).thenReturn(bookEntity);
        when(bookRepository.save(any())).thenReturn(bookEntity);
        when(bookMapper.toModel(bookEntity)).thenReturn(book);

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
        when(bookRepository.findById("b1")).thenReturn(Optional.of(bookEntity));
        when(bookMapper.toModel(bookEntity)).thenReturn(book);

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
        when(bookRepository.findAll()).thenReturn(List.of(bookEntity));
        when(bookMapper.toModel(bookEntity)).thenReturn(book);

        List<Book> result = bookService.getAllBooks();
        assertEquals(1, result.size());
    }

    @Test
    void decrementCopies_shouldReduceAvailableCopies() {
        when(bookRepository.findById("b1")).thenReturn(Optional.of(bookEntity));
        when(bookRepository.save(any())).thenReturn(bookEntity);

        bookService.decrementCopies("b1");

        assertEquals(4, bookEntity.getAvailableCopies());
        verify(bookRepository).save(bookEntity);
    }

    @Test
    void decrementCopies_shouldThrowWhenNoCopiesAvailable() {
        bookEntity.setAvailableCopies(0);
        when(bookRepository.findById("b1")).thenReturn(Optional.of(bookEntity));

        assertThrows(BookNotAvailableException.class, () -> bookService.decrementCopies("b1"));
    }

    @Test
    void incrementCopies_shouldIncreaseAvailableCopies() {
        bookEntity.setAvailableCopies(3);
        when(bookRepository.findById("b1")).thenReturn(Optional.of(bookEntity));
        when(bookRepository.save(any())).thenReturn(bookEntity);

        bookService.incrementCopies("b1");

        assertEquals(4, bookEntity.getAvailableCopies());
        verify(bookRepository).save(bookEntity);
    }

    @Test
    void incrementCopies_shouldThrowWhenAlreadyAtMax() {
        bookEntity.setAvailableCopies(5);
        when(bookRepository.findById("b1")).thenReturn(Optional.of(bookEntity));

        assertThrows(IllegalStateException.class, () -> bookService.incrementCopies("b1"));
    }
}