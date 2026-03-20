package edu.eci.dosw.tdd;

import edu.eci.dosw.tdd.controller.dto.UserDTO;
import edu.eci.dosw.tdd.controller.mapper.UserMapper;
import edu.eci.dosw.tdd.core.model.User;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserMapperTest {

    private final UserMapper mapper = new UserMapper();

    @Test
    void toModel_shouldMapCorrectly() {
        UserDTO dto = new UserDTO();
        dto.setId("1");
        dto.setName("Juan");

        User user = mapper.toModel(dto);

        assertEquals("1", user.getId());
        assertEquals("Juan", user.getName());
    }

    @Test
    void toDTO_shouldMapCorrectly() {
        User user = new User("1", "Juan");

        UserDTO dto = mapper.toDTO(user);

        assertEquals("1", dto.getId());
        assertEquals("Juan", dto.getName());
    }
}