package edu.eci.dosw.tdd;

import edu.eci.dosw.tdd.core.exception.UserNotFoundException;
import edu.eci.dosw.tdd.core.model.User;
import edu.eci.dosw.tdd.core.service.UserService;
import edu.eci.dosw.tdd.persistence.repository.UserRepositoryPort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepositoryPort userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserService userService;

    private User user;

    @BeforeEach
    void setUp() {
        user = new User();
        user.setId("u1");
        user.setUsername("jdoe");
        user.setPasswordHash("hashed123");
        user.setName("John Doe");
        user.setRole("USER");
    }

    @Test
    void registerUser_shouldEncodePasswordAndSave() {
        when(passwordEncoder.encode(anyString())).thenReturn("encodedPassword");
        when(userRepository.save(any())).thenReturn(user);

        User result = userService.registerUser(user);

        assertNotNull(result);
        assertEquals("u1", result.getId());
        verify(passwordEncoder).encode(anyString());
        verify(userRepository).save(any());
    }

    @Test
    void getUserById_shouldReturnUser() {
        when(userRepository.findById("u1")).thenReturn(Optional.of(user));

        User result = userService.getUserById("u1");
        assertEquals("u1", result.getId());
    }

    @Test
    void getUserById_shouldThrowWhenNotFound() {
        when(userRepository.findById("x")).thenReturn(Optional.empty());
        assertThrows(UserNotFoundException.class, () -> userService.getUserById("x"));
    }

    @Test
    void getAllUsers_shouldReturnList() {
        when(userRepository.findAll()).thenReturn(List.of(user));

        List<User> result = userService.getAllUsers();
        assertEquals(1, result.size());
    }

    @Test
    void deleteUser_shouldCallRepository() {
        doNothing().when(userRepository).deleteById("u1");
        userService.deleteUser("u1");
        verify(userRepository).deleteById("u1");
    }
}