package com.veterinaria.demo.service;

import com.veterinaria.demo.enums.AnimalSpecies;
import com.veterinaria.demo.exception.OperationNotAllowedException;
import com.veterinaria.demo.model.dto.animal.AnimalRequestDTO;
import com.veterinaria.demo.model.dto.animal.AnimalResponseDTO;
import com.veterinaria.demo.exception.ResourceNotFoundException;
import com.veterinaria.demo.model.dto.animal.AnimalUpdateDTO;
import com.veterinaria.demo.model.entity.Animal;
import com.veterinaria.demo.model.mapper.AnimalMapper;
import com.veterinaria.demo.repository.AnimalRepository;
import com.veterinaria.demo.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class AnimalService {

    private final AnimalRepository animalRepository;
    private final AnimalMapper animalMapper;
    private final CustomerRepository customerRepository;

    public List<AnimalResponseDTO> getAllAnimals() {
        return animalRepository.findAll().stream().map(animalMapper::toAnimalResponseDTO).toList();
    }

    public AnimalResponseDTO getAnimalById(Long id) {
        return animalRepository.findById(id).map(animalMapper::toAnimalResponseDTO)
                .orElseThrow(() -> new ResourceNotFoundException("Animal not found: " + id));
    }

    public List<AnimalResponseDTO> getAnimalsByFilter(String name, String breed, AnimalSpecies species, Long tutorId) {
        return animalRepository.findByFilter(name, breed, species, tutorId).stream()
                .map(animalMapper::toAnimalResponseDTO).toList();
    }

    @Transactional
    public AnimalResponseDTO createAnimal(AnimalRequestDTO animalDTO) {
        var tutor = customerRepository.findById(animalDTO.tutorId())
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found: " + animalDTO.tutorId()));

        var animalMapped = animalMapper.toAnimal(animalDTO, tutor);
        animalMapped.setCreationDate(LocalDate.now());
        var animalSaved = animalRepository.save(animalMapped);

        return animalMapper.toAnimalResponseDTO(animalSaved);
    }

    @Transactional
    public AnimalResponseDTO updateAnimal(Long id, AnimalUpdateDTO animalDTO) {
        var animal = animalRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Animal not found: " + id));

        applyUpdates(animal, animalDTO);
        var updatedAnimal = animalRepository.save(animal);
        return animalMapper.toAnimalResponseDTO(updatedAnimal);
    }

    @Transactional
    public void deleteAnimal(Long id) {
        var animalToDelete = animalRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Animal not found: " + id));
        animalRepository.delete(animalToDelete);
    }

    private void applyUpdates(Animal animal, AnimalUpdateDTO animalDTO) {
        Optional.ofNullable(animalDTO.name())
                .filter(name -> !name.isBlank())
                .ifPresent(animal::setName);

        Optional.ofNullable(animalDTO.breed())
                .filter(breed -> !breed.isBlank())
                .ifPresent(animal::setBreed);

        Optional.ofNullable(animalDTO.age())
                .filter(age -> age > 0)
                .ifPresent(animal::setAge);
    }

    private void validateDuplicatedRegister(Animal animal) {
        var exists = animalRepository.existsByNameAndTutorId(animal.getName(), animal.getTutor().getId());
        if (exists) {
            throw new OperationNotAllowedException("This animal is already registered for this tutor");
        }
    }
}
