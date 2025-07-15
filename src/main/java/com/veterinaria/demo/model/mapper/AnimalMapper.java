package com.veterinaria.demo.model.mapper;

import com.veterinaria.demo.model.dto.animal.AnimalRequestDTO;
import com.veterinaria.demo.model.dto.animal.AnimalResponseDTO;
import com.veterinaria.demo.model.entity.Animal;
import com.veterinaria.demo.model.entity.Customer;
import org.springframework.stereotype.Component;

@Component
public class AnimalMapper {

    public Animal toAnimal(AnimalRequestDTO animalDTO, Customer tutor) {
        return Animal.builder()
                .name(animalDTO.name())
                .age(animalDTO.age())
                .breed(animalDTO.breed())
                .species(animalDTO.species())
                .tutor(tutor)
                .build();
    }

    public AnimalResponseDTO toAnimalResponseDTO(Animal animal) {
        return AnimalResponseDTO.builder()
                .id(animal.getId())
                .name(animal.getName())
                .age(animal.getAge())
                .species(animal.getSpecies())
                .breed(animal.getBreed())
                .creationDate(animal.getCreationDate())
                .tutor(AnimalResponseDTO.CustomerDTO.builder()
                        .id(animal.getTutor().getId())
                        .name(animal.getTutor().getName())
                        .build())
                .build();
    }
}
