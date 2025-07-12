package com.gosterim360.controller;

import com.gosterim360.common.BaseResponse;
import com.gosterim360.dto.request.UserRequestDTO;
import com.gosterim360.dto.response.UserResponseDTO;
import com.gosterim360.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
@Slf4j
public class UserController {

    private final UserService userService;

    @Operation(summary = "Create a new user", description = "Registers a new user with roles")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "User created successfully"),
            @ApiResponse(responseCode = "409", description = "User already exists")
    })
    @PostMapping
    public ResponseEntity<BaseResponse<UserResponseDTO>> createUser(
            @Valid @RequestBody UserRequestDTO requestDTO) {

        log.info("UserController::createUser called");

        UserResponseDTO responseDTO = userService.createUser(requestDTO);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(BaseResponse.success(responseDTO, "User created successfully", HttpStatus.CREATED.value()));
    }

    @Operation(summary = "Get all users", description = "Retrieves a list of all users")
    @ApiResponse(responseCode = "200", description = "Users retrieved successfully")
    @GetMapping
    public ResponseEntity<BaseResponse<List<UserResponseDTO>>> getAllUsers() {
        log.info("UserController::getAllUsers called");

        List<UserResponseDTO> users = userService.getAllUsers();
        return ResponseEntity.ok(
                BaseResponse.success(users, "Users retrieved successfully", HttpStatus.OK.value()));
    }

    @Operation(summary = "Get user by ID", description = "Retrieves a user by their UUID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "User retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "User not found")
    })
    @GetMapping("/{id}")
    public ResponseEntity<BaseResponse<UserResponseDTO>> getUserById(
            @Parameter(description = "UUID of the user") @PathVariable UUID id) {

        log.info("UserController::getUserById called - ID: {}", id);

        UserResponseDTO user = userService.getUserById(id);
        return ResponseEntity.ok(
                BaseResponse.success(user, "User retrieved successfully", HttpStatus.OK.value()));
    }

    @Operation(summary = "Update user", description = "Updates an existing user by ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "User updated successfully"),
            @ApiResponse(responseCode = "404", description = "User not found")
    })
    @PutMapping("/{id}")
    public ResponseEntity<BaseResponse<UserResponseDTO>> updateUser(
            @Parameter(description = "UUID of the user to update") @PathVariable UUID id,
            @Valid @RequestBody UserRequestDTO requestDTO) {

        log.info("UserController::updateUser called - ID: {}", id);

        UserResponseDTO updated = userService.updateUser(id, requestDTO);
        return ResponseEntity.ok(
                BaseResponse.success(updated, "User updated successfully", HttpStatus.OK.value()));
    }

    @Operation(summary = "Delete user", description = "Deletes a user by ID")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "User deleted successfully"),
            @ApiResponse(responseCode = "404", description = "User not found")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<BaseResponse<Void>> deleteUserById(
            @Parameter(description = "UUID of the user to delete") @PathVariable UUID id) {

        log.info("UserController::deleteUserById called - ID: {}", id);

        userService.deleteUserById(id);
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .body(BaseResponse.success(null, "User deleted successfully", HttpStatus.NO_CONTENT.value()));
    }
}
