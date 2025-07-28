package com.gosterim360.dto.response;

import com.gosterim360.enums.ReservationStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.util.UUID;

@Getter
@Setter
@Builder
@Schema(name = "ReservationResponseDTO", description = "Response DTO representing a reservation with all details")
public class ReservationResponseDTO {

    @Schema(description = "Unique identifier of the reservation", example = "c3d4e5f6-7890-12ab-cdef-3456789012cd")
    private UUID id;

    @Schema(description = "Session details for which the seat is reserved")
    private SessionResponseDTO session;

    @Schema(description = "Reserved seat details")
    private SeatResponseDTO seat;

    @Schema(description = "Status of the reservation. Allowed values: PRE_RESERVED, PAID", example = "PRE_RESERVED")
    private ReservationStatus status;

    @Schema(description = "Creation timestamp of the reservation", example = "2025-07-08T10:30:00Z")
    private Instant createdAt;

    @Schema(description = "Last update timestamp of the reservation", example = "2025-07-09T11:15:00Z")
    private Instant updatedAt;


}