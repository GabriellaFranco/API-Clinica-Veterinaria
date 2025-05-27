package com.veterinaria.demo.dto.animal;

import com.veterinaria.demo.entity.Customer;
import com.veterinaria.demo.enums.AnimalSpecies;
import jakarta.persistence.Column;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Builder;

@Builder
public record CreateAnimalDTO(

        @Size(min = 2)
        @NotNull
        String name,

        @NotNull
        AnimalSpecies species,

        @NotNull
        String breed,

        @Positive
        @NotNull
        int age,

        @NotNull
        Long tutor_id
) {
}
