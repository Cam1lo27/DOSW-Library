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

    private final Map<String, Book> books = new HashMap<>();
    private final Map<String, Integer> bookCopies = new HashMap<>();

    public Book addBook(Book book, int copies) {
        book.setCopies(copies);
        book.setAvailable(copies > 0);
        books.put(book.getId(), book);
        bookCopies.put(book.getId(), copies);
        return book;
    }

    public List<Book> getAllBooks() {
        return new ArrayList<>(books.values());
    }

    public Book getBookById(String id) {
        Book book = books.get(id);
        if (book == null) throw new BookNotAvailableException(id);
        return book;
    }

    public Book updateAvailability(String id, boolean available) {
        Book book = getBookById(id);
        book.setAvailable(available);
        return book;
    }

    public void decrementCopies(String id) {
        Book book = getBookById(id);
        int copies = bookCopies.get(id);
        if (copies <= 0) throw new BookNotAvailableException(id);
        int newCopies = copies - 1;
        bookCopies.put(id, newCopies);
        book.setCopies(newCopies);
        book.setAvailable(newCopies > 0);
    }

    public void incrementCopies(String id) {
        Book book = getBookById(id);
        int newCopies = bookCopies.get(id) + 1;
        bookCopies.put(id, newCopies);
        book.setCopies(newCopies);
        book.setAvailable(true);
    }
}