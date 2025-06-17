package com.veterinaria.demo.model.mapper;

import com.veterinaria.demo.model.dto.procedure.ProcedureRequestDTO;
import com.veterinaria.demo.model.dto.procedure.ProcedureResponseDTO;
import com.veterinaria.demo.model.entity.Animal;
import com.veterinaria.demo.model.entity.Procedure;
import com.veterinaria.demo.model.entity.User;
import org.springframework.stereotype.Component;

@Component
public class ProcedureMapper {

    public Procedure toProcedure(ProcedureRequestDTO procedureDTO, User veterinarian, Animal animal) {
        return Procedure.builder()
                .type(procedureDTO.type())
                .price(procedureDTO.price())
                .description(procedureDTO.description())
                .animal(animal)
                .veterinarian(veterinarian)
                .build();
    }

    public ProcedureResponseDTO toProcedureResponseDTO(Procedure procedure) {
        return ProcedureResponseDTO.builder()
                .id(procedure.getId())
                .type(procedure.getType())
                .price(procedure.getPrice())
                .description(procedure.getDescription())
                .date(procedure.getDate())
                .animal(ProcedureResponseDTO.AnimalDTO.builder()
                        .id(procedure.getAnimal().getId())
                        .name(procedure.getAnimal().getName())
                        .build())
                .veterinarian(ProcedureResponseDTO.UserDTO.builder()
                        .id(procedure.getVeterinarian().getId())
                        .name(procedure.getVeterinarian().getName())
                        .crmv_number(procedure.getVeterinarian().getCrmv_number())
                        .build())
                .build();

    }
}
