package com.veterinaria.demo.model.dto.procedure;

import com.veterinaria.demo.model.entity.Animal;
import com.veterinaria.demo.model.entity.User;
import com.veterinaria.demo.enums.ProcedureType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Builder;

import java.math.BigDecimal;

@Builder
public record ProcedureRequestDTO(

        @NotNull
        ProcedureType type,

        @NotNull
        @Positive(message = "The price must be a positive value")
        BigDecimal price,

        @NotNull @NotBlank
        @Size(min = 20, max = 250, message = "The description must have 20-250 characters")
        String description,

        @NotNull
        Animal animalId,

        @NotNull
        User veterinarianId

        ) {
}
