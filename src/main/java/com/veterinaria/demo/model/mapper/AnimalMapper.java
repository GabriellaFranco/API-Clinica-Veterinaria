package com.veterinaria.demo.model.mapper;

import com.veterinaria.demo.model.dto.animal.CreateAnimalDTO;
import com.veterinaria.demo.model.dto.animal.GetAnimalDTO;
import com.veterinaria.demo.model.entity.Animal;
import com.veterinaria.demo.model.entity.Customer;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class AnimalMapper {

    public Animal toAnimal(CreateAnimalDTO animalDTO, Customer tutor) {
        return Animal.builder()
                .name(animalDTO.name())
                .age(animalDTO.age())
                .breed(animalDTO.breed())
                .species(animalDTO.species())
                .tutor(tutor)
                .build();
    }

    public GetAnimalDTO toGetAnimalDTO(Animal animal) {
        return GetAnimalDTO.builder()
                .id(animal.getId())
                .name(animal.getName())
                .age(animal.getAge())
                .species(animal.getSpecies())
                .breed(animal.getBreed())
                .creationDate(animal.getCreationDate())
                .tutor(GetAnimalDTO.CustomerDTO.builder()
                        .id(animal.getTutor().getId())
                        .name(animal.getTutor().getName())
                        .build())
                .build();
    }
}
