package edu.eci.dosw.DOSW_Library;

import edu.eci.dosw.tdd.core.util.IdGeneratorUtil;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class IdGeneratorUtilTest {

    private final IdGeneratorUtil idGeneratorUtil = new IdGeneratorUtil();

    @Test
    void generateId_shouldReturnNonNullId() {
        assertNotNull(idGeneratorUtil.generateId());
    }

    @Test
    void generateId_shouldReturnUniqueIds() {
        assertNotEquals(idGeneratorUtil.generateId(), idGeneratorUtil.generateId());
    }
}