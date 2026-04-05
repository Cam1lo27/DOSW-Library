package edu.eci.dosw.tdd.controller.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UserDTO {

    @NotBlank(message = "El id es obligatorio")
    private String id;

    @NotBlank(message = "El username es obligatorio")
    private String username;

    @NotBlank(message = "La contraseña es obligatoria")
    @Size(min = 8, message = "La contraseña debe tener al menos 8 caracteres")
    private String password;

    @NotBlank(message = "El nombre es obligatorio")
    private String name;

    @NotBlank(message = "El rol es obligatorio")
    private String role;
}
