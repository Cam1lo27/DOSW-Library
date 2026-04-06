package edu.eci.dosw.tdd.persistence.nonrelational;

import edu.eci.dosw.tdd.core.model.Book;
import edu.eci.dosw.tdd.persistence.nonrelational.mapper.BookDocumentMapper;
import edu.eci.dosw.tdd.persistence.nonrelational.repository.BookMongoRepository;
import edu.eci.dosw.tdd.persistence.repository.BookRepositoryPort;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
@Profile("mongo")
public class BookRepositoryMongoImpl implements BookRepositoryPort {

    private final BookMongoRepository repository;
    private final BookDocumentMapper mapper;

    public BookRepositoryMongoImpl(BookMongoRepository repository, BookDocumentMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public Book save(Book book) {
        return mapper.toDomain(repository.save(mapper.toDocument(book)));
    }

    @Override
    public Optional<Book> findById(String id) {
        return repository.findById(id).map(mapper::toDomain);
    }

    @Override
    public List<Book> findAll() {
        return repository.findAll().stream().map(mapper::toDomain).collect(Collectors.toList());
    }

    @Override
    public void delete(String id) {
        repository.deleteById(id);
    }
}