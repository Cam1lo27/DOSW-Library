package edu.eci.dosw.tdd;

import edu.eci.dosw.tdd.controller.dto.BookDTO;
import edu.eci.dosw.tdd.controller.mapper.BookMapper;
import edu.eci.dosw.tdd.core.model.Book;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BookMapperTest {

    private final BookMapper mapper = new BookMapper();

    @Test
    void toModel_shouldMapCorrectly() {
        BookDTO dto = new BookDTO();
        dto.setId("1");
        dto.setTitle("Clean Code");
        dto.setAuthor("Robert Martin");

        Book book = mapper.toModel(dto);

        assertEquals("1", book.getId());
        assertEquals("Clean Code", book.getTitle());
        assertEquals("Robert Martin", book.getAuthor());
    }

    @Test
    void toDTO_shouldMapCorrectly() {
        Book book = new Book("1", "Clean Code", "Robert Martin");

        BookDTO dto = mapper.toDTO(book);

        assertEquals("1", dto.getId());
        assertEquals("Clean Code", dto.getTitle());
        assertEquals("Robert Martin", dto.getAuthor());
    }
}