package edu.eci.dosw.tdd.core.validator;

import edu.eci.dosw.tdd.core.model.User;
import org.springframework.stereotype.Component;

@Component
public class UserValidator {

    public void validate(User user) {
        if (user.getName() == null || user.getName().isBlank())
            throw new IllegalArgumentException("El nombre del usuario no puede estar vacío.");
    }
}