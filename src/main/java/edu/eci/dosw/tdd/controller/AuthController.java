package edu.eci.dosw.tdd.controller;

import edu.eci.dosw.tdd.controller.dto.LoginRequestDTO;
import edu.eci.dosw.tdd.controller.dto.LoginResponseDTO;
import edu.eci.dosw.tdd.controller.dto.UserDTO;
import edu.eci.dosw.tdd.controller.mapper.UserMapper;
import edu.eci.dosw.tdd.core.service.AuthService;
import edu.eci.dosw.tdd.core.service.UserService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;
    private final UserService userService;
    private final UserMapper userMapper;

    public AuthController(AuthService authService, UserService userService, UserMapper userMapper) {
        this.authService = authService;
        this.userService = userService;
        this.userMapper = userMapper;
    }

    @PostMapping("/login")
    public LoginResponseDTO login( @Valid @RequestBody LoginRequestDTO request) {
        return new LoginResponseDTO(authService.login(request.getUsername(), request.getPassword()));
    }

    @PostMapping("/register")
    public UserDTO register( @Valid @RequestBody UserDTO dto) {
        return userMapper.toDTO(userService.registerUser(userMapper.toModel(dto)));
    }
}