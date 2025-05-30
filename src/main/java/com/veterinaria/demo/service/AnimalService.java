package com.veterinaria.demo.service;

import com.veterinaria.demo.enums.AnimalSpecies;
import com.veterinaria.demo.model.dto.animal.CreateAnimalDTO;
import com.veterinaria.demo.model.dto.animal.GetAnimalDTO;
import com.veterinaria.demo.exception.ResourceNotFoundException;
import com.veterinaria.demo.model.mapper.AnimalMapper;
import com.veterinaria.demo.repository.AnimalRepository;
import com.veterinaria.demo.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@RequiredArgsConstructor
@Service
public class AnimalService {

    private final AnimalRepository animalRepository;
    private final AnimalMapper animalMapper;
    private final CustomerRepository customerRepository;

    public List<GetAnimalDTO> getAllAnimals() {
        return animalRepository.findAll().stream().map(animalMapper::toGetAnimalDTO).toList();
    }

    public GetAnimalDTO getAnimalById(Long id) {
        return animalRepository.findById(id).map(animalMapper::toGetAnimalDTO)
                .orElseThrow(() -> new ResourceNotFoundException("Animal not found: " + id));
    }

    public GetAnimalDTO getAnimalByName(String name) {
        return animalRepository.findByNameIgnoreCase(name).map(animalMapper::toGetAnimalDTO)
                .orElseThrow(() -> new ResourceNotFoundException("Animal not found: " + name));
    }

    public List<GetAnimalDTO> getAnimalsByTutorId(Long tutorId) {
        if (!customerRepository.existsById(tutorId)) {
            throw new ResourceNotFoundException("Customer not found: " + tutorId);
        }
        return animalRepository.findByTutorId(tutorId).stream().map(animalMapper::toGetAnimalDTO).toList();
    }

    public List<GetAnimalDTO> getAnimalsBySpecies(String species) {
        var animalSpecies = parseSpecies(species);
        return animalRepository.findBySpecies(animalSpecies).stream().map(animalMapper::toGetAnimalDTO).toList();
    }

    @Transactional
    public GetAnimalDTO createAnimal(CreateAnimalDTO animalDTO) {
        var tutor = customerRepository.findById(animalDTO.tutorId())
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found: " + animalDTO.tutorId()));

        var animalMapped = animalMapper.toAnimal(animalDTO, tutor);
        animalMapped.setCreationDate(LocalDate.now());
        var animalSaved = animalRepository.save(animalMapped);

        return animalMapper.toGetAnimalDTO(animalSaved);
    };

    private AnimalSpecies parseSpecies(String species) {
        try {
            return AnimalSpecies.valueOf(species.toUpperCase());
        } catch (IllegalArgumentException exc) {
            throw new ResourceNotFoundException("Species not found: " + species);
        }
    }
}
