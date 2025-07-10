package com.gosterim360.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@Builder
@Schema(name = "SessionRequestDTO", description = "Session Request DTO")
public class SessionRequestDTO {

    @NotNull(message = "Date cannot be null")
    @Schema(description = "Session date", example = "2025-07-05", required = true)
    private LocalDate date;


    @NotEmpty(message = "Session times cannot be empty")
    @Schema(description = "List of session times", required = true)
    private List<SessionTimeRequestDTO> times;

    @NotNull(message = "Price cannot be null")
    @DecimalMin(value = "0.0", inclusive = false, message = "Price must be positive")
    @Schema(description = "Price of the session", example = "95.5", required = true)
    private BigDecimal price;
}
