package com.veterinaria.demo.model.mapper;

import com.veterinaria.demo.model.dto.user.CreateUserDTO;
import com.veterinaria.demo.model.dto.user.GetUserDTO;
import com.veterinaria.demo.model.entity.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public User toUser(CreateUserDTO userDTO) {
        return User.builder()
                .name(userDTO.name())
                .email(userDTO.email())
                .password(userDTO.password())
                .crmv_number(userDTO.crmv_number())
                .build();
    }

    public GetUserDTO toGetUserDTO(User user) {
        return GetUserDTO.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .crmv_number(user.getCrmv_number())
                .profile(user.getProfile())
                .build();
    }
}
