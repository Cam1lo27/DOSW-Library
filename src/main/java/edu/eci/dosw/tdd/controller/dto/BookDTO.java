package edu.eci.dosw.tdd.controller.dto;

public class BookDTO {
    private String id;
    private String title;
    private String author;
    private int copies;

    public String getId() { return id; }
    public String getTitle() { return title; }
    public String getAuthor() { return author; }
    public int getCopies() { return copies; }
    public void setId(String id) { this.id = id; }
    public void setTitle(String title) { this.title = title; }
    public void setAuthor(String author) { this.author = author; }
    public void setCopies(int copies) { this.copies = copies; }
}