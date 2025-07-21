package com.gosterim360.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@Schema(name = "SessionTimeRequestDTO", description = "Session Time Request DTO")
public class SessionTimeRequestDTO {

    @NotNull(message = "Time cannot be null")
    @Schema(description = "Session time", example = "20:00:00", required = true)
    private LocalDateTime time;

    @NotNull(message = "Price cannot be null")
    @Schema(description = "Price of the session", example = "95.5", required = true)
    private BigDecimal price;
}