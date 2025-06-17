package com.veterinaria.demo.model.dto.customer;

import lombok.Builder;

@Builder
public record CustomerUpdateDTO(
        String name,
        String phone
) {
}
