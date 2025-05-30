package com.veterinaria.demo.service;

import com.veterinaria.demo.enums.ProcedureType;
import com.veterinaria.demo.exception.ResourceNotFoundException;
import com.veterinaria.demo.model.dto.procedure.CreateProcedureDTO;
import com.veterinaria.demo.model.dto.procedure.GetProcedureDTO;
import com.veterinaria.demo.model.mapper.ProcedureMapper;
import com.veterinaria.demo.repository.AnimalRepository;
import com.veterinaria.demo.repository.ProcedureRepository;
import com.veterinaria.demo.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class ProcedureService {

    private final ProcedureRepository procedureRepository;
    private final AnimalRepository animalRepository;
    private final UserRepository userRepository;
    private final ProcedureMapper procedureMapper;

    public List<GetProcedureDTO> getAllProcedures() {
        return procedureRepository.findAll().stream().map(procedureMapper::toGetProcedureDTO).toList();
    }

    public GetProcedureDTO getProcedureById(Long id) {
        return procedureRepository.findById(id).map(procedureMapper::toGetProcedureDTO)
                .orElseThrow(() -> new ResourceNotFoundException("Procedure not found: " + id));
    }

    public List<GetProcedureDTO> getAllProceduresByType(String type) {
        var procedureType = parseProcedureType(type);
        return procedureRepository.findByType(procedureType).stream().map(procedureMapper::toGetProcedureDTO).toList();
    }

    public List<GetProcedureDTO> getAllProceduresByVeterinarianId(Long id) {
        if (!userRepository.existsById(id)) {
            throw new ResourceNotFoundException("Veterinarian not found: " + id);
        }
        return procedureRepository.findByVeterinarianId(id).stream().map(procedureMapper::toGetProcedureDTO).toList();
    }

    public List<GetProcedureDTO> getAllProceduresByAnimalId(Long id) {
        if (!animalRepository.existsById(id)) {
            throw new ResourceNotFoundException("Animal not found: " + id);
        }
        return procedureRepository.findByAnimalId(id).stream().map(procedureMapper::toGetProcedureDTO).toList();
    }

    @Transactional
    public GetProcedureDTO createProcedure(CreateProcedureDTO procedureDTO) {
        var animal = animalRepository.findById(procedureDTO.animalId().getId())
                .orElseThrow(() -> new ResourceNotFoundException("Animal not found:" + procedureDTO.animalId().getId()));
        var veterinarian = userRepository.findById(procedureDTO.veterinarianId().getId())
                .orElseThrow(() -> new ResourceNotFoundException("User not found: " + procedureDTO.veterinarianId().getId()));

        var procedureMapped = procedureMapper.toProcedure(procedureDTO, veterinarian, animal);
        var procedureSaved = procedureRepository.save(procedureMapped);

        return procedureMapper.toGetProcedureDTO(procedureSaved);
    }

    private ProcedureType parseProcedureType(String type) {
        try {
            return ProcedureType.valueOf(type.toUpperCase());
        } catch (IllegalArgumentException exc) {
            throw new ResourceNotFoundException("Procedure type not found: " + type);
        }
    }

}
