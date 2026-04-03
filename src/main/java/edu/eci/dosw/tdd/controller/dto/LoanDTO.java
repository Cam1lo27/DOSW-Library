package edu.eci.dosw.tdd.controller.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class LoanDTO {

    @NotBlank(message = "El userId es obligatorio")
    private String userId;

    @NotBlank(message = "El bookId es obligatorio")
    private String bookId;
}