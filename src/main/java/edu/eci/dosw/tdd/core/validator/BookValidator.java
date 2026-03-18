package edu.eci.dosw.tdd.core.validator;

import edu.eci.dosw.tdd.core.model.Book;
import org.springframework.stereotype.Component;

@Component
public class BookValidator {

    public void validate(Book book) {
        if (book.getTitle() == null || book.getTitle().isBlank())
            throw new IllegalArgumentException("El título del libro no puede estar vacío.");
        if (book.getAuthor() == null || book.getAuthor().isBlank())
            throw new IllegalArgumentException("El autor del libro no puede estar vacío.");
    }
}