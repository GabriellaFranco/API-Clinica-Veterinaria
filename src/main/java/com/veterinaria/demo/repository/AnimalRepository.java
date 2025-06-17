package com.veterinaria.demo.repository;

import com.veterinaria.demo.enums.AnimalSpecies;
import com.veterinaria.demo.model.entity.Animal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AnimalRepository extends JpaRepository<Animal, Long> {

    @Query("""
    SELECT a FROM Animal a
    WHERE (:name IS NULL OR LOWER(a.name) LIKE LOWER(CONCAT('%', :name, '%')))
      AND (:breed IS NULL OR LOWER(a.breed) LIKE LOWER(CONCAT('%', :breed, '%')))
      AND (:species IS NULL OR a.species = :species)
      AND (:tutor_id IS NULL OR a.tutor.id = :tutor_id)
""")
    List<Animal> findByFilter(String name, String breed, AnimalSpecies species, Long customerId);

    boolean existsByNameAndTutorId(String name, Long tutorId);

}
