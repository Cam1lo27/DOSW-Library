package edu.eci.dosw.tdd.core.util;

import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class IdGeneratorUtil {

    public String generateId() {
        return UUID.randomUUID().toString();
    }
}