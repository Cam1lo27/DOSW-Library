package edu.eci.dosw.tdd.core.service;

import edu.eci.dosw.tdd.core.exception.BookNotAvailableException;
import edu.eci.dosw.tdd.core.model.Book;
import edu.eci.dosw.tdd.persistence.dao.BookEntity;
import edu.eci.dosw.tdd.persistence.mapper.BookPersistenceMapper;
import edu.eci.dosw.tdd.persistence.repository.BookRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BookService {

    private final BookRepository bookRepository;
    private final BookPersistenceMapper bookMapper;

    public BookService(BookRepository bookRepository, BookPersistenceMapper bookMapper) {
        this.bookRepository = bookRepository;
        this.bookMapper = bookMapper;
    }

    public Book addBook(Book book) {
        if (book.getTotalCopies() <= 0)
            throw new IllegalArgumentException("El total de copias debe ser mayor a 0.");
        book.setAvailableCopies(book.getTotalCopies());
        return bookMapper.toModel(bookRepository.save(bookMapper.toEntity(book)));
    }

    public List<Book> getAllBooks() {
        return bookRepository.findAll().stream()
                .map(bookMapper::toModel)
                .collect(Collectors.toList());
    }

    public Book getBookById(String id) {
        return bookRepository.findById(id)
                .map(bookMapper::toModel)
                .orElseThrow(() -> new BookNotAvailableException(id));
    }

    public BookEntity getEntityById(String id) {
        return bookRepository.findById(id)
                .orElseThrow(() -> new BookNotAvailableException(id));
    }

    public void decrementCopies(String id) {
        BookEntity entity = getEntityById(id);
        if (entity.getAvailableCopies() <= 0)
            throw new BookNotAvailableException(id);
        entity.setAvailableCopies(entity.getAvailableCopies() - 1);
        bookRepository.save(entity);
    }

    public void incrementCopies(String id) {
        BookEntity entity = getEntityById(id);
        if (entity.getAvailableCopies() >= entity.getTotalCopies())
            throw new IllegalStateException("No se pueden superar las copias totales.");
        entity.setAvailableCopies(entity.getAvailableCopies() + 1);
        bookRepository.save(entity);
    }
}