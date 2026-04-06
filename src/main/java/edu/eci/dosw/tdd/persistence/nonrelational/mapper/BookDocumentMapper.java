package edu.eci.dosw.tdd.persistence.nonrelational.mapper;

import edu.eci.dosw.tdd.core.model.Book;
import edu.eci.dosw.tdd.persistence.nonrelational.document.BookDocument;
import org.springframework.stereotype.Component;

@Component
public class BookDocumentMapper {

    public BookDocument toDocument(Book book) {
        BookDocument doc = new BookDocument();
        doc.setId(book.getId());
        doc.setTitle(book.getTitle());
        doc.setAuthor(book.getAuthor());
        doc.setIsbn(book.getIsbn());
        doc.setPublicationType(book.getPublicationType());
        doc.setPublishDate(book.getPublishDate());
        doc.setAddedAt(book.getAddedAt());
        doc.setCategories(book.getCategories());
        doc.setTotalCopies(book.getTotalCopies());
        doc.setAvailableCopies(book.getAvailableCopies());
        doc.setLoanedCopies(book.getLoanedCopies());
        doc.setStatus(book.getStatus());
        if (book.getMetadata() != null) {
            doc.setMetadata(new BookDocument.BookMetadata(
                    book.getMetadata().getPages(),
                    book.getMetadata().getLanguage(),
                    book.getMetadata().getPublisher()
            ));
        }
        return doc;
    }

    public Book toDomain(BookDocument doc) {
        Book book = new Book();
        book.setId(doc.getId());
        book.setTitle(doc.getTitle());
        book.setAuthor(doc.getAuthor());
        book.setIsbn(doc.getIsbn());
        book.setPublicationType(doc.getPublicationType());
        book.setPublishDate(doc.getPublishDate());
        book.setAddedAt(doc.getAddedAt());
        book.setCategories(doc.getCategories());
        book.setTotalCopies(doc.getTotalCopies());
        book.setAvailableCopies(doc.getAvailableCopies());
        book.setLoanedCopies(doc.getLoanedCopies());
        book.setStatus(doc.getStatus());
        if (doc.getMetadata() != null) {
            book.setMetadata(new Book.BookMetadata(
                    doc.getMetadata().getPages(),
                    doc.getMetadata().getLanguage(),
                    doc.getMetadata().getPublisher()
            ));
        }
        return book;
    }
}