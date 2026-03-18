package edu.eci.dosw.DOSW_Library;

import edu.eci.dosw.tdd.core.model.User;
import edu.eci.dosw.tdd.core.validator.UserValidator;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserValidatorTest {

    private final UserValidator validator = new UserValidator();

    @Test
    void validate_shouldPass_whenUserIsValid() {
        User user = new User("1", "Juan");
        assertDoesNotThrow(() -> validator.validate(user));
    }

    @Test
    void validate_shouldThrow_whenNameIsBlank() {
        User user = new User("1", "");
        assertThrows(IllegalArgumentException.class, () -> validator.validate(user));
    }
}