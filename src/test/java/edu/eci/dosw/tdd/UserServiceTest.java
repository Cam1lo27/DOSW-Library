package edu.eci.dosw.tdd;

import edu.eci.dosw.tdd.core.exception.UserNotFoundException;
import edu.eci.dosw.tdd.core.model.User;
import edu.eci.dosw.tdd.core.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class UserServiceTest {

    private UserService userService;

    @BeforeEach
    void setUp() {
        userService = new UserService();
    }

    @Test
    void registerUser_shouldRegisterSuccessfully() {
        User user = new User("1", "Juan");
        userService.registerUser(user);

        User result = userService.getUserById("1");
        assertEquals("Juan", result.getName());
    }

    @Test
    void getAllUsers_shouldReturnAllUsers() {
        userService.registerUser(new User("1", "Juan"));
        userService.registerUser(new User("2", "Maria"));

        List<User> users = userService.getAllUsers();
        assertEquals(2, users.size());
    }

    @Test
    void getUserById_shouldReturnUser_whenExists() {
        userService.registerUser(new User("1", "Juan"));

        User result = userService.getUserById("1");
        assertNotNull(result);
        assertEquals("1", result.getId());
    }

    @Test
    void getUserById_shouldThrowException_whenNotExists() {
        assertThrows(UserNotFoundException.class, () -> userService.getUserById("999"));
    }
}