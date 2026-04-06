package edu.eci.dosw.tdd.core.service;

import edu.eci.dosw.tdd.core.exception.BookNotAvailableException;
import edu.eci.dosw.tdd.core.model.Book;
import edu.eci.dosw.tdd.persistence.repository.BookRepositoryPort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService {

    private final BookRepositoryPort bookRepository;

    public BookService(BookRepositoryPort bookRepository) {
        this.bookRepository = bookRepository;
    }

    public Book addBook(Book book) {
        if (book.getTotalCopies() <= 0)
            throw new IllegalArgumentException("El total de copias debe ser mayor a 0.");
        book.setAvailableCopies(book.getTotalCopies());
        return bookRepository.save(book);
    }

    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    public Book getBookById(String id) {
        return bookRepository.findById(id)
                .orElseThrow(() -> new BookNotAvailableException(id));
    }

    public Book decrementCopies(String id) {
        Book book = getBookById(id);
        if (book.getAvailableCopies() <= 0)
            throw new BookNotAvailableException(id);
        book.setAvailableCopies(book.getAvailableCopies() - 1);
        return bookRepository.save(book);
    }

    public Book incrementCopies(String id) {
        Book book = getBookById(id);
        if (book.getAvailableCopies() >= book.getTotalCopies())
            throw new IllegalStateException("No se pueden superar las copias totales.");
        book.setAvailableCopies(book.getAvailableCopies() + 1);
        return bookRepository.save(book);
    }
}