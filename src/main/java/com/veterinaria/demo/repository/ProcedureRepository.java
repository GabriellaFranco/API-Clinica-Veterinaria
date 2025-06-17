package com.veterinaria.demo.repository;

import com.veterinaria.demo.enums.ProcedureType;
import com.veterinaria.demo.model.entity.Procedure;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ProcedureRepository extends JpaRepository<Procedure, Long> {

    @Query("""
    SELECT p FROM Procedure p
    WHERE (:type IS NULL OR p.type = :type)
      AND (:date IS NULL OR p.date = :date)
      AND (:animalId IS NULL OR p.animal.id = :animalId)
      AND (:veterinarianId IS NULL OR p.veterinarian.id = :veterinarianId)
""")
    List<Procedure> findByFilter(ProcedureType type, LocalDate date, Long animalId, Long veterinarianId);
}
