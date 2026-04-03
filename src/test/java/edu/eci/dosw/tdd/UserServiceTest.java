package edu.eci.dosw.tdd;

import edu.eci.dosw.tdd.core.exception.UserNotFoundException;
import edu.eci.dosw.tdd.core.model.User;
import edu.eci.dosw.tdd.core.service.UserService;
import edu.eci.dosw.tdd.persistence.dao.UserEntity;
import edu.eci.dosw.tdd.persistence.mapper.UserPersistenceMapper;
import edu.eci.dosw.tdd.persistence.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserPersistenceMapper userMapper;

    @InjectMocks
    private UserService userService;

    private UserEntity userEntity;
    private User user;

    @BeforeEach
    void setUp() {
        userEntity = new UserEntity("u1", "jdoe", "hashed123", "John Doe", "USER");
        user = new User("u1", "jdoe", "hashed123", "John Doe", "USER");
    }

    @Test
    void registerUser_shouldSaveAndReturnUser() {
        when(userMapper.toEntity(any())).thenReturn(userEntity);
        when(userRepository.save(any())).thenReturn(userEntity);
        when(userMapper.toModel(userEntity)).thenReturn(user);

        User result = userService.registerUser(user);

        assertNotNull(result);
        assertEquals("u1", result.getId());
        verify(userRepository).save(any());
    }

    @Test
    void getUserById_shouldReturnUser() {
        when(userRepository.findById("u1")).thenReturn(Optional.of(userEntity));
        when(userMapper.toModel(userEntity)).thenReturn(user);

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
        when(userRepository.findAll()).thenReturn(List.of(userEntity));
        when(userMapper.toModel(userEntity)).thenReturn(user);

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