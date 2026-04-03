package edu.eci.dosw.tdd.controller.dto;

import lombok.Data;

@Data
public class UserDTO {
    private String id;
    private String username;
    private String password;
    private String name;
    private String role;
}