package edu.eci.dosw.tdd.controller;

import edu.eci.dosw.tdd.controller.dto.BookDTO;
import edu.eci.dosw.tdd.controller.mapper.BookMapper;
import edu.eci.dosw.tdd.core.service.BookService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/books")
public class BookController {

    private final BookService bookService;
    private final BookMapper bookMapper;

    public BookController(BookService bookService, BookMapper bookMapper) {
        this.bookService = bookService;
        this.bookMapper = bookMapper;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasRole('LIBRARIAN')")
    public BookDTO addBook( @Valid @RequestBody BookDTO dto) {
        return bookMapper.toDTO(bookService.addBook(bookMapper.toModel(dto)));
    }

    @GetMapping
    @PreAuthorize("hasRole('USER') or hasRole('LIBRARIAN')")
    public List<BookDTO> getAllBooks() {
        return bookService.getAllBooks().stream()
                .map(bookMapper::toDTO)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('USER') or hasRole('LIBRARIAN')")
    public BookDTO getBookById(@PathVariable String id) {
        return bookMapper.toDTO(bookService.getBookById(id));
    }
}