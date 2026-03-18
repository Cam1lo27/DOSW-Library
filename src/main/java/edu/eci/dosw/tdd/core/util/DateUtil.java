package edu.eci.dosw.tdd.core.util;

import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class DateUtil {

    public boolean isBeforeToday(LocalDate date) {
        return date.isBefore(LocalDate.now());
    }

    public boolean isAfterToday(LocalDate date) {
        return date.isAfter(LocalDate.now());
    }
}