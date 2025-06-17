package com.veterinaria.demo.model.dto.user;

import com.veterinaria.demo.enums.UserProfile;
import lombok.Builder;

@Builder
public record UserResponseDTO(
        Long id,
        String name,
        String email,
        String crmv_number,
        UserProfile profile
) {}
