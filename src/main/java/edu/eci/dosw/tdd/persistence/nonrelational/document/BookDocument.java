package edu.eci.dosw.tdd.persistence.nonrelational.document;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "books")
public class BookDocument {

    @Id
    private String id;
    private String title;
    private String author;
    private String isbn;
    private String publicationType;
    private LocalDate publishDate;
    private LocalDate addedAt;
    private List<String> categories;
    private BookMetadata metadata;
    private int totalCopies;
    private int availableCopies;
    private int loanedCopies;
    private String status;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class BookMetadata {
        private int pages;
        private String language;
        private String publisher;
    }
}