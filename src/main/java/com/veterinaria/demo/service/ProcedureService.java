package com.veterinaria.demo.service;

import com.veterinaria.demo.enums.ProcedureType;
import com.veterinaria.demo.exception.ResourceNotFoundException;
import com.veterinaria.demo.model.dto.procedure.ProcedureRequestDTO;
import com.veterinaria.demo.model.dto.procedure.ProcedureResponseDTO;
import com.veterinaria.demo.model.dto.procedure.ProcedureUpdateDTO;
import com.veterinaria.demo.model.entity.Procedure;
import com.veterinaria.demo.model.mapper.ProcedureMapper;
import com.veterinaria.demo.repository.AnimalRepository;
import com.veterinaria.demo.repository.ProcedureRepository;
import com.veterinaria.demo.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

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

    @Transactional
    public ProcedureResponseDTO updateProcedure(Long id, ProcedureUpdateDTO procedureDTO) {
        var procedure = procedureRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Procedure not found: " + id));

        applyUpdate(procedure, procedureDTO);
        var updatedProcedure = procedureRepository.save(procedure);
        return procedureMapper.toProcedureResponseDTO(updatedProcedure);
    }

    @Transactional
    public void deleteProcedure(Long id) {
        var procedure = procedureRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Procedure not found: " + id));
        procedureRepository.delete(procedure);
    }

    private void applyUpdate(Procedure procedure, ProcedureUpdateDTO procedureDTO) {
        Optional.ofNullable(procedureDTO.description())
                .filter(description -> !description.isBlank())
                .ifPresent(procedure::setDescription);

        Optional.ofNullable(procedureDTO.price())
                .filter(price -> price.compareTo(BigDecimal.ZERO) > 0)
                .ifPresent(procedure::setPrice);
    }

}
