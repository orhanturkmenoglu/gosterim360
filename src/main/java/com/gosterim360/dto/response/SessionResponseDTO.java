package com.gosterim360.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Builder
@Schema(name = "SessionResponseDTO", description = "Session Response DTO")
public class SessionResponseDTO {

    @Schema(description = "Session unique identifier", example = "3fa85f64-5717-4562-b3fc-2c963f66afa6")
    private UUID id;

    @Schema(description = "Session date", example = "2025-07-05")
    private LocalDate date;

    @Schema(description = "List of session times")
    private List<SessionTimeResponseDTO> times;

    @Schema(description = "Creation date of the session", example = "2024-03-28T12:00:00Z")
    private Instant createdAt;

    @Schema(description = "Last update date of the session", example = "2024-03-28T12:00:00Z")
    private Instant updatedAt;
}
