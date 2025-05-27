package com.veterinaria.demo.service;

import com.veterinaria.demo.dto.animal.CreateAnimalDTO;
import com.veterinaria.demo.dto.animal.GetAnimalDTO;
import com.veterinaria.demo.exception.ResourceNotFoundException;
import com.veterinaria.demo.mapper.AnimalMapper;
import com.veterinaria.demo.repository.AnimalRepository;
import com.veterinaria.demo.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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

    public GetAnimalDTO createAnimal(CreateAnimalDTO animalDTO) {
        var tutor = customerRepository.findById(animalDTO.tutor_id())
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found: " + animalDTO.tutor_id()));

        var animalMapped = animalMapper.toAnimal(animalDTO, tutor);
        var animalSaved = animalRepository.save(animalMapped);

        return animalMapper.toGetAnimalDTO(animalSaved);
    };
}
