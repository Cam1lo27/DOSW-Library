package edu.eci.dosw.tdd.controller.dto;

public class LoanDTO {
    private String userId;
    private String bookId;

    public String getUserId() { return userId; }
    public String getBookId() { return bookId; }
    public void setUserId(String userId) { this.userId = userId; }
    public void setBookId(String bookId) { this.bookId = bookId; }
}