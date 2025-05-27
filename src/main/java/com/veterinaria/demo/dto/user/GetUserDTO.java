package com.veterinaria.demo.dto.user;

import com.veterinaria.demo.enums.UserProfile;
import lombok.Builder;

@Builder
public record GetUserDTO(
        Long id,
        String name,
        String email,
        String crmv_number,
        UserProfile profile
) {}
