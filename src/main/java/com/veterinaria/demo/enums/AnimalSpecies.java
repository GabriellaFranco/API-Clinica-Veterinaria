package com.veterinaria.demo.enums;

import com.veterinaria.demo.exception.ResourceNotFoundException;

public enum AnimalSpecies {
    CAT,
    DOG,
    RODENT,
    BIRD,
    REPTILE,
    EXOTIC;

    private AnimalSpecies parseSpecies(String species) {
        try {
            return AnimalSpecies.valueOf(species.toUpperCase());
        } catch (IllegalArgumentException exc) {
            throw new ResourceNotFoundException("Species not found: " + species);
        }
    }
}
