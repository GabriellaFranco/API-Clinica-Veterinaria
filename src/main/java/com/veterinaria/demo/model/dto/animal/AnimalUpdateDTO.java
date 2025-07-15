package com.veterinaria.demo.model.dto.animal;

import lombok.Builder;

@Builder
public record AnimalUpdateDTO(
        String name,
        String breed,
        Long age
) {
}
