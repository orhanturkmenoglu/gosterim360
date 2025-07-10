package com.gosterim360.controller;

import com.gosterim360.common.BaseResponse;
import com.gosterim360.dto.request.ReservationRequestDTO;
import com.gosterim360.dto.response.ReservationResponseDTO;
import com.gosterim360.service.ReservationService;
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
@RequestMapping("/api/v1reservations")
@RequiredArgsConstructor
@Tag(name = "Reservation", description = "Reservation management APIs")
public class ReservationController {

    private final ReservationService reservationService;

    @PostMapping
    @Operation(summary = "Create a new reservation")
    public ResponseEntity<BaseResponse<ReservationResponseDTO>> createReservation(
            @Valid @RequestBody ReservationRequestDTO request,
            HttpServletRequest httpRequest) {
        ReservationResponseDTO response = reservationService.create(request);
        BaseResponse<ReservationResponseDTO> baseResponse = BaseResponse.success(
                response,
                "Reservation created successfully",
                HttpStatus.CREATED.value()
        );
        baseResponse.setPath(httpRequest.getRequestURI());
        return ResponseEntity.status(HttpStatus.CREATED).body(baseResponse);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update an existing reservation")
    public ResponseEntity<BaseResponse<ReservationResponseDTO>> updateReservation(
            @PathVariable UUID id,
            @Valid @RequestBody ReservationRequestDTO request,
            HttpServletRequest httpRequest) {
        ReservationResponseDTO response = reservationService.update(id, request);
        BaseResponse<ReservationResponseDTO> baseResponse = BaseResponse.success(
                response,
                "Reservation updated successfully",
                HttpStatus.OK.value()
        );
        baseResponse.setPath(httpRequest.getRequestURI());
        return ResponseEntity.ok(baseResponse);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a reservation by ID")
    public ResponseEntity<BaseResponse<Void>> deleteReservation(
            @PathVariable UUID id,
            HttpServletRequest httpRequest) {
        reservationService.delete(id);
        BaseResponse<Void> baseResponse = BaseResponse.success(
                null,
                "Reservation deleted successfully",
                HttpStatus.OK.value()
        );
        baseResponse.setPath(httpRequest.getRequestURI());
        return ResponseEntity.ok(baseResponse);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get a reservation by ID")
    public ResponseEntity<BaseResponse<ReservationResponseDTO>> getReservationById(
            @PathVariable UUID id,
            HttpServletRequest httpRequest) {
        ReservationResponseDTO response = reservationService.getById(id);
        BaseResponse<ReservationResponseDTO> baseResponse = BaseResponse.success(
                response,
                "Reservation fetched successfully",
                HttpStatus.OK.value()
        );
        baseResponse.setPath(httpRequest.getRequestURI());
        return ResponseEntity.ok(baseResponse);
    }

    @GetMapping
    @Operation(summary = "Get all reservations")
    public ResponseEntity<BaseResponse<List<ReservationResponseDTO>>> getAllReservations(
            HttpServletRequest httpRequest) {
        List<ReservationResponseDTO> response = reservationService.getAll();
        BaseResponse<List<ReservationResponseDTO>> baseResponse = BaseResponse.success(
                response,
                "Reservations fetched successfully",
                HttpStatus.OK.value()
        );
        baseResponse.setPath(httpRequest.getRequestURI());
        return ResponseEntity.ok(baseResponse);
    }

    @GetMapping("/session/{sessionId}")
    @Operation(summary = "Get all reservations for a specific session")
    public ResponseEntity<BaseResponse<List<ReservationResponseDTO>>> getReservationsBySessionId(
            @PathVariable UUID sessionId,
            HttpServletRequest httpRequest) {
        List<ReservationResponseDTO> response = reservationService.getBySessionId(sessionId);
        BaseResponse<List<ReservationResponseDTO>> baseResponse = BaseResponse.success(
                response,
                "Reservations for session fetched successfully",
                HttpStatus.OK.value()
        );
        baseResponse.setPath(httpRequest.getRequestURI());
        return ResponseEntity.ok(baseResponse);
    }
}