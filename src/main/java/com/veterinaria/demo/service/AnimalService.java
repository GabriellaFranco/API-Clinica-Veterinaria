package com.veterinaria.demo.service;

import com.veterinaria.demo.enums.AnimalSpecies;
import com.veterinaria.demo.model.dto.animal.AnimalRequestDTO;
import com.veterinaria.demo.model.dto.animal.AnimalResponseDTO;
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

}
