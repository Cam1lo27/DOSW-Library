package edu.eci.dosw.tdd.controller.mapper;

import edu.eci.dosw.tdd.controller.dto.BookDTO;
import edu.eci.dosw.tdd.core.model.Book;
import org.springframework.stereotype.Component;

@Component
public class BookMapper {

    public Book toModel(BookDTO dto) {
        return new Book(dto.getId(), dto.getTitle(), dto.getAuthor());
    }

    public BookDTO toDTO(Book book) {
        BookDTO dto = new BookDTO();
        dto.setId(book.getId());
        dto.setTitle(book.getTitle());
        dto.setAuthor(book.getAuthor());
        return dto;
    }
}