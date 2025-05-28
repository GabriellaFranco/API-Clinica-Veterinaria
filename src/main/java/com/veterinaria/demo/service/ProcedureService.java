package com.veterinaria.demo.service;

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

    @Transactional
    public GetProcedureDTO createProcedure(CreateProcedureDTO procedureDTO) {
        var animal = animalRepository.findById(procedureDTO.animal_id().getId())
                .orElseThrow(() -> new ResourceNotFoundException("Animal not found:" + procedureDTO.animal_id().getId()));
        var veterinarian = userRepository.findById(procedureDTO.veterinarian_id().getId())
                .orElseThrow(() -> new ResourceNotFoundException("User not found: " + procedureDTO.veterinarian_id().getId()));

        var procedureMapped = procedureMapper.toProcedure(procedureDTO, veterinarian, animal);
        var procedureSaved = procedureRepository.save(procedureMapped);

        return procedureMapper.toGetProcedureDTO(procedureSaved);
    }

}
