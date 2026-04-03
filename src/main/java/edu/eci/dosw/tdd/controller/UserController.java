package edu.eci.dosw.tdd.controller;

import edu.eci.dosw.tdd.controller.dto.UserDTO;
import edu.eci.dosw.tdd.controller.mapper.UserMapper;
import edu.eci.dosw.tdd.core.model.User;
import edu.eci.dosw.tdd.core.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;
    private final UserMapper userMapper;

    public UserController(UserService userService, UserMapper userMapper) {
        this.userService = userService;
        this.userMapper = userMapper;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasRole('LIBRARIAN')")
    public UserDTO registerUser( @Valid @RequestBody UserDTO dto) {
        return userMapper.toDTO(userService.registerUser(userMapper.toModel(dto)));
    }

    @GetMapping
    @PreAuthorize("hasRole('LIBRARIAN')")
    public List<UserDTO> getAllUsers() {
        return userService.getAllUsers().stream()
                .map(userMapper::toDTO)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('LIBRARIAN')")
    public UserDTO getUserById(@PathVariable String id) {
        return userMapper.toDTO(userService.getUserById(id));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('LIBRARIAN')")
    public UserDTO updateUser(@PathVariable String id, @RequestBody UserDTO dto) {
        return userMapper.toDTO(
                userService.registerUser(userMapper.toModel(dto))
        );
    }

    @PatchMapping("/{id}")
    @PreAuthorize("hasRole('LIBRARIAN')")
    public UserDTO patchUser(@PathVariable String id, @RequestBody UserDTO dto) {
        User user = userService.getUserById(id);

        if (dto.getName() != null) user.setName(dto.getName());
        if (dto.getUsername() != null) user.setUsername(dto.getUsername());
        if (dto.getRole() != null) user.setRole(dto.getRole());

        return userMapper.toDTO(userService.registerUser(user));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('LIBRARIAN')")
    public void deleteUser(@PathVariable String id) {
        userService.deleteUser(id);
    }
}