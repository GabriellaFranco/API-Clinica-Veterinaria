package com.veterinaria.demo.model.dto.procedure;

import com.veterinaria.demo.model.entity.Animal;
import com.veterinaria.demo.model.entity.User;
import com.veterinaria.demo.enums.ProcedureType;
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
        @Positive
        BigDecimal price,

        @NotNull
        @Size(min = 20, max = 250)
        String description,

        @NotNull
        Animal animalId,

        @NotNull
        User veterinarianId

        ) {

}
