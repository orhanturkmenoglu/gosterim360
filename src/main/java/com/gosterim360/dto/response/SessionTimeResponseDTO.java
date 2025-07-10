package com.gosterim360.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.UUID;

@Getter
@Setter
@Builder
@Schema(name = "SessionTimeResponseDTO", description = "Session Time Response DTO")
public class SessionTimeResponseDTO {

    @Schema(description = "Session time id", example = "123e4567-e89b-12d3-a456-426614174000")
    private UUID id;

    @Schema(description = "Session time", example = "20:00:00")
    private LocalDateTime time;

    @Schema(description = "Creation date of the session time", example = "2024-03-28T12:00:00Z")
    private Instant createdAt;

    @Schema(description = "Last update date of the session time", example = "2024-03-28T12:00:00Z")
    private Instant updatedAt;
}
