package com.veterinaria.demo.service;

import com.veterinaria.demo.enums.ProcedureType;
import com.veterinaria.demo.exception.ResourceNotFoundException;
import com.veterinaria.demo.model.dto.procedure.ProcedureRequestDTO;
import com.veterinaria.demo.model.dto.procedure.ProcedureResponseDTO;
import com.veterinaria.demo.model.mapper.ProcedureMapper;
import com.veterinaria.demo.repository.AnimalRepository;
import com.veterinaria.demo.repository.ProcedureRepository;
import com.veterinaria.demo.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@RequiredArgsConstructor
@Service
public class ProcedureService {

    private final ProcedureRepository procedureRepository;
    private final AnimalRepository animalRepository;
    private final UserRepository userRepository;
    private final ProcedureMapper procedureMapper;

    public List<ProcedureResponseDTO> getAllProcedures() {
        return procedureRepository.findAll().stream().map(procedureMapper::toProcedureResponseDTO).toList();
    }

    public ProcedureResponseDTO getProcedureById(Long id) {
        return procedureRepository.findById(id).map(procedureMapper::toProcedureResponseDTO)
                .orElseThrow(() -> new ResourceNotFoundException("Procedure not found: " + id));
    }

    public List<ProcedureResponseDTO> getProceduresByFilter(ProcedureType type, LocalDate date, Long animalId,
                                                            Long veterinarianId) {

        return procedureRepository.findByFilter(type, date, animalId, veterinarianId)
                .stream().map(procedureMapper::toProcedureResponseDTO).toList();
    }

    @Transactional
    public ProcedureResponseDTO createProcedure(ProcedureRequestDTO procedureDTO) {
        var animal = animalRepository.findById(procedureDTO.animalId().getId())
                .orElseThrow(() -> new ResourceNotFoundException("Animal not found:" + procedureDTO.animalId().getId()));
        var veterinarian = userRepository.findById(procedureDTO.veterinarianId().getId())
                .orElseThrow(() -> new ResourceNotFoundException("User not found: " + procedureDTO.veterinarianId().getId()));

        var procedureMapped = procedureMapper.toProcedure(procedureDTO, veterinarian, animal);
        var procedureSaved = procedureRepository.save(procedureMapped);

        return procedureMapper.toProcedureResponseDTO(procedureSaved);
    }

    private ProcedureType parseProcedureType(String type) {
        try {
            return ProcedureType.valueOf(type.toUpperCase());
        } catch (IllegalArgumentException exc) {
            throw new ResourceNotFoundException("Procedure type not found: " + type);
        }
    }

}
