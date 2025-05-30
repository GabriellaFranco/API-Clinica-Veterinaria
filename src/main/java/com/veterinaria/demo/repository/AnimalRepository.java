package com.veterinaria.demo.repository;

import com.veterinaria.demo.enums.AnimalSpecies;
import com.veterinaria.demo.model.entity.Animal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AnimalRepository extends JpaRepository<Animal, Long> {

    Optional<Animal> findByNameIgnoreCase(String name);

    List<Animal> findByTutorId(Long id);

    List<Animal> findBySpecies(AnimalSpecies species);

}
