package com.gosterim360.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SeatRequestDTO {


    @Schema(description = "Row number of the seat", example = "5", required = true, minimum = "1")
    @NotNull(message = "Row number must not be null")
    @Min(value = 1, message = "Row number must be at least 1")
    private Integer rowNumber;

    @Schema(description = "Seat number within the row", example = "8", required = true, minimum = "1")
    @NotNull(message = "Seat number must not be null")
    @Min(value = 1, message = "Seat number must be at least 1")
    private Integer seatNumber;
}