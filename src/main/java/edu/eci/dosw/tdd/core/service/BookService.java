package edu.eci.dosw.tdd.core.service;

import edu.eci.dosw.tdd.core.exception.BookNotAvailableException;
import edu.eci.dosw.tdd.core.model.Book;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class BookService {

    private final Map<Book, Integer> bookCopies = new HashMap<>();

    public Book addBook(Book book, int copies) {
        bookCopies.put(book, copies);
        return book;
    }

    public List<Book> getAllBooks() {
        return new ArrayList<>(bookCopies.keySet());
    }

    public Book getBookById(String id) {
        return bookCopies.keySet().stream()
                .filter(b -> b.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new BookNotAvailableException(id));
    }

    public Book updateAvailability(String id, boolean available) {
        Book book = getBookById(id);
        book.setAvailable(available);
        return book;
    }

    public void decrementCopies(String id) {
        Book book = getBookById(id);
        int copies = bookCopies.get(book);
        if (copies <= 0) throw new BookNotAvailableException(id);
        bookCopies.put(book, copies - 1);
        if (copies - 1 == 0) book.setAvailable(false);
    }

    public void incrementCopies(String id) {
        Book book = getBookById(id);
        bookCopies.put(book, bookCopies.get(book) + 1);
        book.setAvailable(true);
    }
}