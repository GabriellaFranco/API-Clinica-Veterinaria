package com.veterinaria.demo.model.dto.user;

import lombok.Builder;

@Builder
public record UserUpdateDTO(
        String name,
        String email
) {}
