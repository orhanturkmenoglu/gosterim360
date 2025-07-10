package com.gosterim360.controller;

import com.gosterim360.common.BaseResponse;
import com.gosterim360.dto.request.SalonRequestDTO;
import com.gosterim360.dto.response.SalonResponseDTO;
import com.gosterim360.service.SalonService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/salons")
@RequiredArgsConstructor
@Tag(name = "Salon", description = "Cinema hall management APIs")
public class SalonController {

    private final SalonService salonService;

    @PostMapping
    @Operation(summary = "Create a new cinema hall")
    public ResponseEntity<BaseResponse<SalonResponseDTO>> createSalon(
            @Valid @RequestBody SalonRequestDTO request,
            HttpServletRequest httpRequest) {
        SalonResponseDTO response = salonService.create(request);
        BaseResponse<SalonResponseDTO> baseResponse = BaseResponse.success(
                response,
                "Cinema hall created successfully",
                HttpStatus.CREATED.value()
        );
        baseResponse.setPath(httpRequest.getRequestURI());
        return ResponseEntity.status(HttpStatus.CREATED).body(baseResponse);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update an existing cinema hall")
    public ResponseEntity<BaseResponse<SalonResponseDTO>> updateSalon(
            @PathVariable UUID id,
            @Valid @RequestBody SalonRequestDTO request,
            HttpServletRequest httpRequest) {
        SalonResponseDTO response = salonService.update(id, request);
        BaseResponse<SalonResponseDTO> baseResponse = BaseResponse.success(
                response,
                "Cinema hall updated successfully",
                HttpStatus.OK.value()
        );
        baseResponse.setPath(httpRequest.getRequestURI());
        return ResponseEntity.ok(baseResponse);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a cinema hall by ID")
    public ResponseEntity<BaseResponse<Void>> deleteSalon(
            @PathVariable UUID id,
            HttpServletRequest httpRequest) {
        salonService.delete(id);
        BaseResponse<Void> baseResponse = BaseResponse.success(
                null,
                "Cinema hall deleted successfully",
                HttpStatus.OK.value()
        );
        baseResponse.setPath(httpRequest.getRequestURI());
        return ResponseEntity.ok(baseResponse);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get a cinema hall by ID")
    public ResponseEntity<BaseResponse<SalonResponseDTO>> getSalonById(
            @PathVariable UUID id,
            HttpServletRequest httpRequest) {
        SalonResponseDTO response = salonService.getById(id);
        BaseResponse<SalonResponseDTO> baseResponse = BaseResponse.success(
                response,
                "Cinema hall fetched successfully",
                HttpStatus.OK.value()
        );
        baseResponse.setPath(httpRequest.getRequestURI());
        return ResponseEntity.ok(baseResponse);
    }

    @GetMapping
    @Operation(summary = "Get all cinema halls")
    public ResponseEntity<BaseResponse<List<SalonResponseDTO>>> getAllSalons(
            HttpServletRequest httpRequest) {
        List<SalonResponseDTO> response = salonService.getAll();
        BaseResponse<List<SalonResponseDTO>> baseResponse = BaseResponse.success(
                response,
                "Cinema halls fetched successfully",
                HttpStatus.OK.value()
        );
        baseResponse.setPath(httpRequest.getRequestURI());
        return ResponseEntity.ok(baseResponse);
    }
}