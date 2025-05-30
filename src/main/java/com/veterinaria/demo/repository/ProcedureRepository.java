package com.veterinaria.demo.repository;

import com.veterinaria.demo.enums.ProcedureType;
import com.veterinaria.demo.model.entity.Procedure;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProcedureRepository extends JpaRepository<Procedure, Long> {

    List<Procedure> findByType(ProcedureType type);

    List<Procedure> findByVeterinarianId(Long veterinarianId);

    List<Procedure> findByAnimalId(Long animalId);
}
