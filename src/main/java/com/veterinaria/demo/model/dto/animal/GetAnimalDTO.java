package com.veterinaria.demo.model.dto.animal;

import com.veterinaria.demo.enums.AnimalSpecies;
import lombok.Builder;

import java.time.LocalDate;

@Builder
public record GetAnimalDTO (
        Long id,
        String name,
        AnimalSpecies species,
        String breed,
        int age,
        LocalDate creationDate,
        CustomerDTO tutor
){
    @Builder
    public record CustomerDTO(
       Long id,
       String name
    ) {}
}
