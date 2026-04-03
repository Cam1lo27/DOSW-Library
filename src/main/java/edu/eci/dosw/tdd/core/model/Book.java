package edu.eci.dosw.tdd.core.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Book {
    private String id;
    private String title;
    private String author;
    private int totalCopies;
    private int availableCopies;

    public boolean isAvailable() {
        return availableCopies > 0;
    }
}