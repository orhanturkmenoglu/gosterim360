package com.gosterim360.controller;

import com.gosterim360.common.BaseResponse;
import com.gosterim360.dto.request.SeatRequestDTO;
import com.gosterim360.dto.response.SeatResponseDTO;
import com.gosterim360.service.SeatService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Tag(name = "Seat", description = "Operations related to cinema seats")
@RestController
@RequestMapping("/api/v1/seats")
@RequiredArgsConstructor
public class SeatController {

    private final SeatService seatService;

    @Operation(summary = "Create a new seat")
    @PostMapping
    public ResponseEntity<BaseResponse<SeatResponseDTO>> createSeat(
            @Valid @RequestBody SeatRequestDTO request) {
        SeatResponseDTO response = seatService.createSeat(request);
        return ResponseEntity.ok(BaseResponse.success(response, "Seat created successfully", 200));
    }

    @Operation(summary = "Get seat by ID")
    @GetMapping("/{id}")
    public ResponseEntity<BaseResponse<SeatResponseDTO>> getSeatById(
            @Parameter(description = "Seat UUID", required = true)
            @PathVariable UUID id) {
        SeatResponseDTO response = seatService.getSeatById(id);
        return ResponseEntity.ok(BaseResponse.success(response, "Seat fetched successfully", 200));
    }

    @Operation(summary = "Get all seats")
    @GetMapping
    public ResponseEntity<BaseResponse<List<SeatResponseDTO>>> getAllSeats() {
        List<SeatResponseDTO> response = seatService.getAllSeats();
        return ResponseEntity.ok(BaseResponse.success(response, "All seats fetched successfully", 200));
    }

    @Operation(summary = "Update seat by ID")
    @PutMapping("/{id}")
    public ResponseEntity<BaseResponse<SeatResponseDTO>> updateSeat(
            @Parameter(description = "Seat UUID", required = true)
            @PathVariable UUID id,
            @Valid @RequestBody SeatRequestDTO request) {
        SeatResponseDTO response = seatService.updateSeat(id, request);
        return ResponseEntity.ok(BaseResponse.success(response, "Seat updated successfully", 200));
    }

    @Operation(summary = "Delete seat by ID")
    @DeleteMapping("/{id}")
    public ResponseEntity<BaseResponse<Void>> deleteSeat(
            @Parameter(description = "Seat UUID", required = true)
            @PathVariable UUID id) {
        seatService.deleteSeat(id);
        return ResponseEntity.ok(BaseResponse.success(null, "Seat deleted successfully", 200));
    }
}