package edu.eci.dosw.tdd.controller.mapper;

import edu.eci.dosw.tdd.controller.dto.UserDTO;
import edu.eci.dosw.tdd.core.model.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public User toModel(UserDTO dto) {
        return new User(dto.getId(), dto.getName());
    }

    public UserDTO toDTO(User user) {
        UserDTO dto = new UserDTO();
        dto.setId(user.getId());
        dto.setName(user.getName());
        return dto;
    }
}