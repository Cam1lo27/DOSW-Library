package edu.eci.dosw.tdd.persistence.mapper;

import edu.eci.dosw.tdd.core.model.Book;
import edu.eci.dosw.tdd.persistence.dao.BookEntity;
import org.springframework.stereotype.Component;

@Component
public class BookPersistenceMapper {

    public BookEntity toEntity(Book book) {
        return new BookEntity(
                book.getId(),
                book.getTitle(),
                book.getAuthor(),
                book.getTotalCopies(),
                book.getAvailableCopies()
        );
    }

    public Book toModel(BookEntity entity) {
        return new Book(
                entity.getId(),
                entity.getTitle(),
                entity.getAuthor(),
                entity.getTotalCopies(),
                entity.getAvailableCopies()
        );
    }
}