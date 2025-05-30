package com.veterinaria.demo.controller;

import com.veterinaria.demo.model.dto.procedure.CreateProcedureDTO;
import com.veterinaria.demo.model.dto.procedure.GetProcedureDTO;
import com.veterinaria.demo.service.ProcedureService;
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
@RequestMapping("/procedures")
public class ProcedureController {

    private final ProcedureService procedureService;

    @Operation(
            summary = "Returns a list with all existing procedures",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Operation successful"),
                    @ApiResponse(responseCode = "204", description = "No content to show")
            }
    )
    @GetMapping
    public ResponseEntity<List<GetProcedureDTO>> getAllProcedures() {
        var procedures = procedureService.getAllProcedures();
        return procedures.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(procedures);
    }

    @Operation(
            summary = "Finds a procedure that matches the id provided",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Operation successful"),
                    @ApiResponse(responseCode = "404", description = "Not found")
            }
    )
    @GetMapping("/{id}")
    public ResponseEntity<GetProcedureDTO> getProcedureById(@PathVariable Long id) {
        return ResponseEntity.ok(procedureService.getProcedureById(id));
    }

    @Operation(
            summary = "Returns a list with all existing procedures of the provided type",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Operation successful"),
                    @ApiResponse(responseCode = "204", description = "No content to show")
            }
    )
    @GetMapping("procedure-type/{type}")
    public ResponseEntity<List<GetProcedureDTO>> getAllProceduresByType(@PathVariable String type) {
        var procedures = procedureService.getAllProceduresByType(type);
        return procedures.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(procedures);
    }

    @Operation(
            summary = "Returns a list with all existing procedures registered to the informed animal id",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Operation successful"),
                    @ApiResponse(responseCode = "204", description = "No content to show")
            }
    )
    @GetMapping("animal/{animalId}")
    public ResponseEntity<List<GetProcedureDTO>> getAllProceduresByAnimalId(@PathVariable Long animalId) {
        var procedures = procedureService.getAllProceduresByAnimalId(animalId);
        return procedures.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(procedures);
    }

    @Operation(
            summary = "Returns a list with all existing procedures registered to the user id provided",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Operation successful"),
                    @ApiResponse(responseCode = "204", description = "No content to show")
            }
    )
    @GetMapping("veterinarian/{vetId}")
    public ResponseEntity<List<GetProcedureDTO>> getAllProceduresByVeterinarianID(@PathVariable Long vetId) {
        var procedures = procedureService.getAllProceduresByVeterinarianId(vetId);
        return procedures.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(procedures);
    }

    @Operation(
            summary = "Creates a new procedure",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Operation successful"),
                    @ApiResponse(responseCode = "400", description = "Invalid data")
            }
    )
    @PostMapping
    public ResponseEntity<GetProcedureDTO> createProcedure(@Valid @RequestBody CreateProcedureDTO procedureDTO) {
        var procedure = procedureService.createProcedure(procedureDTO);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}").buildAndExpand(procedure.id()).toUri();
        return ResponseEntity.created(uri).body(procedure);
    }
}
