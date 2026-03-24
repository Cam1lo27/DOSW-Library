package edu.eci.dosw.tdd;

import edu.eci.dosw.tdd.core.util.DateUtil;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class DateUtilTest {

    private final DateUtil dateUtil = new DateUtil();

    @Test
    void isBeforeToday_shouldReturnTrue_whenDateIsPast() {
        assertTrue(dateUtil.isBeforeToday(LocalDate.now().minusDays(1)));
    }

    @Test
    void isBeforeToday_shouldReturnFalse_whenDateIsFuture() {
        assertFalse(dateUtil.isBeforeToday(LocalDate.now().plusDays(1)));
    }

    @Test
    void isAfterToday_shouldReturnTrue_whenDateIsFuture() {
        assertTrue(dateUtil.isAfterToday(LocalDate.now().plusDays(1)));
    }

    @Test
    void isAfterToday_shouldReturnFalse_whenDateIsPast() {
        assertFalse(dateUtil.isAfterToday(LocalDate.now().minusDays(1)));
    }
}