package com.veterinaria.demo.model.mapper;

import com.veterinaria.demo.model.dto.user.UserRequestDTO;
import com.veterinaria.demo.model.dto.user.UserResponseDTO;
import com.veterinaria.demo.model.entity.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public User toUser(UserRequestDTO userDTO) {
        return User.builder()
                .name(userDTO.name())
                .email(userDTO.email())
                .password(userDTO.password())
                .crmv_number(userDTO.crmv_number())
                .build();
    }

    public UserResponseDTO toUserResponseDTO(User user) {
        return UserResponseDTO.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .crmv_number(user.getCrmv_number())
                .profile(user.getProfile())
                .build();
    }
}
