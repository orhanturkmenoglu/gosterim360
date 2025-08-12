package com.gosterim360.dto.request;

import com.gosterim360.enums.ReservationStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ReservationRequestDTO {

    @Schema(description = "Unique identifier of the user making the reservation", example = "e3f4a5b6-7890-12ab-cdef-3456789012cd")
    @NotNull(message = "User ID must not be null")
    private UUID userId;

    @Schema(description = "Unique identifier of the session for which the seat is being reserved", example = "a1b2c3d4-5678-90ab-cdef-1234567890ab")
    @NotNull(message = "Session ID must not be null")
    private UUID sessionId;

    @Schema(description = "Unique identifier of the seat to be reserved", example = "b2c3d4e5-6789-01ab-cdef-2345678901bc")
    @NotNull(message = "Seat ID must not be null")
    private UUID seatId;
}