package com.veterinaria.demo.controller;

import com.veterinaria.demo.model.dto.user.CreateUserDTO;
import com.veterinaria.demo.model.dto.user.GetUserDTO;
import com.veterinaria.demo.service.UserService;
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
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    @Operation(
            summary = "Returns a list with all existing users",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Operation successful"),
                    @ApiResponse(responseCode = "204", description = "No content to show")
            }
    )
    @GetMapping
    public ResponseEntity<List<GetUserDTO>> getAllUsers() {
        var users = userService.getAllUsers();
        return users.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(users);
    }

    @Operation(
            summary = "Finds a user that matches the id provided",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Operation successful"),
                    @ApiResponse(responseCode = "404", description = "Not found")
            }
    )
    @GetMapping("/{id}")
    public ResponseEntity<GetUserDTO> getUserById(@PathVariable Long id) {
        return ResponseEntity.ok(userService.getUserById(id));
    }

    @Operation(
            summary = "Returns a list of users that matches the name provided",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Operation successful"),
                    @ApiResponse(responseCode = "404", description = "Not found")
            }
    )
    @GetMapping("/name/{name}")
    public ResponseEntity<List<GetUserDTO>> getUserByName(@PathVariable String name) {
        var users = userService.getUserByName(name);
        return users.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(users);
    }

    @Operation(
            summary = "Returns a list with all existing users that match the profile provided",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Operation successful"),
                    @ApiResponse(responseCode = "204", description = "No content to show")
            }
    )
    @GetMapping("/profile/{profile}")
    public ResponseEntity<List<GetUserDTO>> getAllUsersByProfile(@PathVariable String profile) {
        var users = userService.getUsersByProfile(profile);
        return users.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(users);
    }

    @Operation(
            summary = "Creates a new user",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Operation successful"),
                    @ApiResponse(responseCode = "400", description = "Invalid data")
            }
    )
    @PostMapping
    public ResponseEntity<GetUserDTO> createUser(@Valid @RequestBody CreateUserDTO userDTO) {
        var user = userService.createUser(userDTO);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(user.id()).toUri();
        return ResponseEntity.created(uri).body(user);
    }
}
