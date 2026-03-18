package edu.eci.dosw.tdd.core.util;

import org.springframework.stereotype.Component;

@Component
public class ValidationUtil {

    public boolean isNullOrBlank(String value) {
        return value == null || value.isBlank();
    }
}