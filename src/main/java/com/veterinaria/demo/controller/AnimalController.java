package com.veterinaria.demo.controller;

import com.veterinaria.demo.model.dto.animal.CreateAnimalDTO;
import com.veterinaria.demo.model.dto.animal.GetAnimalDTO;
import com.veterinaria.demo.service.AnimalService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/animals")
public class AnimalController {

    private final AnimalService animalService;

    @Operation(
            summary = "Returns a list with all existing animals",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Operation successful"),
                    @ApiResponse(responseCode = "204", description = "No content to show")
            }
    )
    @GetMapping
    public ResponseEntity<List<GetAnimalDTO>> getAllAnimals() {
        var animals = animalService.getAllAnimals();
        return animals.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(animals);
    }

    @Operation(
            summary = "Finds a animal that matches the name provided",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Operation successful"),
                    @ApiResponse(responseCode = "404", description = "Not found")
            }
    )
    @GetMapping("/name/{name}")
    public ResponseEntity<GetAnimalDTO> getAnimalByName(@PathVariable String name) {
        return ResponseEntity.ok(animalService.getAnimalByName(name));
    }

    @Operation(
            summary = "Finds a animal that matches the id provided",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Operation successful"),
                    @ApiResponse(responseCode = "404", description = "Not found")
            }
    )
    @GetMapping("/{id}")
    public ResponseEntity<GetAnimalDTO> getAnimalById(@PathVariable Long id) {
        return ResponseEntity.ok(animalService.getAnimalById(id));
    }

    @Operation(
            summary = "Returns a list with all existing animals registered to a customer",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Operation successful"),
                    @ApiResponse(responseCode = "204", description = "No content to show")
            }
    )
    @GetMapping("/customer/tutorId")
    public ResponseEntity<List<GetAnimalDTO>> getAllAnimalsByTutorId(@PathVariable Long tutorId) {
        var animals = animalService.getAnimalsByTutorId(tutorId);
        return animals.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(animals);
    }

    @Operation(
            summary = "Returns a list with all existing animals registered to a species",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Operation successful"),
                    @ApiResponse(responseCode = "204", description = "No content to show")
            }
    )
    @GetMapping("/species/{species}")
    public ResponseEntity<List<GetAnimalDTO>> getAllAnimalsBySpecies(@PathVariable String species) {
        var animals = animalService.getAnimalsBySpecies(species);
        return animals.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(animals);
    }

    @Operation(
            summary = "Creates a new animal",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Operation successful"),
                    @ApiResponse(responseCode = "400", description = "Invalid data")
            }
    )
    @PostMapping
    public ResponseEntity<GetAnimalDTO> createAnimal(@Valid @RequestBody CreateAnimalDTO animalDTO) {
        var animal = animalService.createAnimal(animalDTO);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(animal.id()).toUri();
        return ResponseEntity.created(uri).body(animal);
    }


}
