package com.veterinaria.demo.controller;

import com.veterinaria.demo.enums.ProcedureType;
import com.veterinaria.demo.model.dto.procedure.ProcedureRequestDTO;
import com.veterinaria.demo.model.dto.procedure.ProcedureResponseDTO;
import com.veterinaria.demo.model.dto.procedure.ProcedureUpdateDTO;
import com.veterinaria.demo.service.ProcedureService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.time.LocalDate;
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
    public ResponseEntity<List<ProcedureResponseDTO>> getAllProcedures() {
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
    public ResponseEntity<ProcedureResponseDTO> getProcedureById(@PathVariable Long id) {
        return ResponseEntity.ok(procedureService.getProcedureById(id));
    }

    @Operation(
            summary = "Creates a new procedure",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Operation successful"),
                    @ApiResponse(responseCode = "400", description = "Invalid data")
            }
    )
    @PostMapping
    public ResponseEntity<ProcedureResponseDTO> createProcedure(@Valid @RequestBody ProcedureRequestDTO procedureDTO) {
        var procedure = procedureService.createProcedure(procedureDTO);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}").buildAndExpand(procedure.id()).toUri();
        return ResponseEntity.created(uri).body(procedure);
    }

    @Operation(
            summary = "Updates the procedure matching the provided id",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Operation successful"),
                    @ApiResponse(responseCode = "400", description = "Invalid data"),
                    @ApiResponse(responseCode = "404", description = "User not found")
            }
    )
    @PutMapping("/{id}")
    public ResponseEntity<ProcedureResponseDTO> updateProcedure(@PathVariable Long id, @RequestBody ProcedureUpdateDTO procedureDTO) {
        return ResponseEntity.ok(procedureService.updateProcedure(id, procedureDTO));
    }

    @Operation(
            summary = "Delete the procedure matching the provided id",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Operation successful"),
                    @ApiResponse(responseCode = "404", description = "User not found"),
            }
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteProcedure(@PathVariable Long id) {
        procedureService.deleteProcedure(id);
        return ResponseEntity.ok("Procedure successfully deleted: " + id);
    }

    @Operation(
            summary = "Returns a list of procedures matching the informed params",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Operation successful"),
                    @ApiResponse(responseCode = "204", description = "No content to show")
            }
    )
    @GetMapping("/search")
    public ResponseEntity<List<ProcedureResponseDTO>> getProceduresByFilter(@RequestParam(required = false) ProcedureType type,
                                                                            @RequestParam(required = false) LocalDate date,
                                                                            @RequestParam(required = false) Long animalId,
                                                                            @RequestParam(required = false) Long veterinarianId) {

        var procedures = procedureService.getProceduresByFilter(type, date, animalId, veterinarianId);
        return procedures.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(procedures);
    }
}
