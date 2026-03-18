package edu.eci.dosw.DOSW_Library;

import edu.eci.dosw.tdd.core.util.ValidationUtil;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ValidationUtilTest {

    private final ValidationUtil validationUtil = new ValidationUtil();

    @Test
    void isNullOrBlank_shouldReturnTrue_whenNull() {
        assertTrue(validationUtil.isNullOrBlank(null));
    }

    @Test
    void isNullOrBlank_shouldReturnTrue_whenBlank() {
        assertTrue(validationUtil.isNullOrBlank("  "));
    }

    @Test
    void isNullOrBlank_shouldReturnFalse_whenHasValue() {
        assertFalse(validationUtil.isNullOrBlank("valor"));
    }
}
