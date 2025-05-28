package com.veterinaria.demo.repository;

import com.veterinaria.demo.model.entity.Procedure;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProcedureRepository extends JpaRepository<Procedure, Long> {
}
