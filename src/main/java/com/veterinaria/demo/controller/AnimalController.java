package com.veterinaria.demo.controller;

import com.veterinaria.demo.enums.AnimalSpecies;
import com.veterinaria.demo.model.dto.animal.AnimalRequestDTO;
import com.veterinaria.demo.model.dto.animal.AnimalResponseDTO;
import com.veterinaria.demo.model.dto.animal.AnimalUpdateDTO;
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
    public ResponseEntity<List<AnimalResponseDTO>> getAllAnimals() {
        var animals = animalService.getAllAnimals();
        return animals.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(animals);
    }


    @Operation(
            summary = "Finds a animal that matches the id provided",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Operation successful"),
                    @ApiResponse(responseCode = "404", description = "Not found")
            }
    )
    @GetMapping("/{id}")
    public ResponseEntity<AnimalResponseDTO> getAnimalById(@PathVariable Long id) {
        return ResponseEntity.ok(animalService.getAnimalById(id));
    }

    @Operation(
            summary = "Creates a new animal",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Operation successful"),
                    @ApiResponse(responseCode = "400", description = "Invalid data")
            }
    )
    @PostMapping
    public ResponseEntity<AnimalResponseDTO> createAnimal(@Valid @RequestBody AnimalRequestDTO animalDTO) {
        var animal = animalService.createAnimal(animalDTO);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(animal.id()).toUri();
        return ResponseEntity.created(uri).body(animal);
    }

    @Operation(
            summary = "Updates the animal matching the provided id",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Operation successful"),
                    @ApiResponse(responseCode = "400", description = "Invalid data"),
                    @ApiResponse(responseCode = "404", description = "User not found")
            }
    )
    @PutMapping("/{id}")
    public ResponseEntity<AnimalResponseDTO> updateAnimal(@PathVariable Long id, @RequestBody AnimalUpdateDTO animalDTO) {
        return ResponseEntity.ok(animalService.updateAnimal(id, animalDTO));
    }

    @Operation(
            summary = "Delete the animal matching the provided id",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Operation successful"),
                    @ApiResponse(responseCode = "404", description = "User not found")
            }
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteAnimal(@PathVariable Long id) {
        animalService.deleteAnimal(id);
        return ResponseEntity.ok("Animal successfully deleted: " + id);
    }

    @Operation(
            summary = "Returns a list of animals matching the informed params",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Operation successful"),
                    @ApiResponse(responseCode = "204", description = "No content to show")
            }
    )
    @GetMapping("/search")
    public ResponseEntity<List<AnimalResponseDTO>> getAnimalsByFilter(@RequestParam(required = false) String name,
                                                                      @RequestParam(required = false) String breed,
                                                                      @RequestParam(required = false) AnimalSpecies species,
                                                                      @RequestParam(required = false) Long tutorId) {

        var animals = animalService.getAnimalsByFilter(name, breed, species, tutorId);
        return animals.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(animals);
    }
}
