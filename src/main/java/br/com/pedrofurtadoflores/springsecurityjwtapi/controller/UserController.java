package br.com.pedrofurtadoflores.springsecurityjwtapi.controller;

import br.com.pedrofurtadoflores.springsecurityjwtapi.dto.request.UserRequestDTO;
import br.com.pedrofurtadoflores.springsecurityjwtapi.dto.response.UserResponseDTO;
import br.com.pedrofurtadoflores.springsecurityjwtapi.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService service;

    public UserController(UserService service) {
        this.service = service;
    }

    @Operation(summary = "List all users", description = "Returns a list of all registered users.")
    @ApiResponse(responseCode = "200", description = "User list returned successfully")
    @GetMapping
    public ResponseEntity<List<UserResponseDTO>> getAll() {
        return ResponseEntity.ok(service.getAll());
    }

    @Operation(summary = "Get user by ID", description = "Returns the data of a specific user.")
    @ApiResponse(responseCode = "200", description = "User found")
    @ApiResponse(responseCode = "404", description = "User not found")
    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDTO> getById(
            @Parameter(description = "User ID") @PathVariable Long id) {
        return ResponseEntity.ok(service.getById(id));
    }

    @Operation(summary = "Create a new user", description = "Creates a new user with the provided data.")
    @ApiResponse(responseCode = "201", description = "User successfully created")
    @ApiResponse(responseCode = "400", description = "Invalid data")
    @PostMapping
    public ResponseEntity<UserResponseDTO> create(@Valid @RequestBody UserRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.create(dto));
    }

    @Operation(summary = "Update an existing user", description = "Updates the user data by ID.")
    @ApiResponse(responseCode = "200", description = "User successfully updated")
    @ApiResponse(responseCode = "404", description = "User not found")
    @PutMapping("/{id}")
    public ResponseEntity<UserResponseDTO> update(
            @Parameter(description = "User ID") @PathVariable Long id,
            @Valid @RequestBody UserRequestDTO dto) {
        return ResponseEntity.ok(service.update(id, dto));
    }

    @Operation(summary = "Delete a user", description = "Removes a user from the database.")
    @ApiResponse(responseCode = "204", description = "User successfully deleted")
    @ApiResponse(responseCode = "404", description = "User not found")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            @Parameter(description = "User ID") @PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
