package com.gosterim360.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.util.UUID;

@Getter
@Setter
@Builder
@Schema(name = "SalonResponseDTO", description = "Response DTO representing a cinema hall with all details")
public class SalonResponseDTO {

    @Schema(description = "Unique identifier of the cinema hall", example = "b1a7e2c0-8e2a-4b1a-9c2e-123456789abc")
    private UUID id;

    @Schema(description = "The name of the cinema hall", example = "Grand London Cinema", maxLength = 100)
    private String name;

    @Schema(description = "The location or address of the cinema hall", example = "221B Baker Street, London", maxLength = 200)
    private String location;

    @Schema(description = "Total seat capacity of the cinema hall", example = "150", minimum = "1")
    private Integer seatCapacity;

    @Schema(description = "Creation timestamp of the cinema hall record", example = "2025-07-08T10:30:00Z")
    private Instant createdAt;

    @Schema(description = "Last update timestamp of the cinema hall record", example = "2025-07-09T11:15:00Z")
    private Instant updatedAt;
}