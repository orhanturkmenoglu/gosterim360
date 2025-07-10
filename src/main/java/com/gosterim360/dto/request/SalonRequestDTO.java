package com.gosterim360.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SalonRequestDTO {

    @Schema(
            description = "The name of the cinema hall",
            example = "Grand London Cinema",
            maxLength = 100
    )
    @NotBlank(message = "Name must not be blank")
    @Size(max = 100, message = "Name must be at most 100 characters")
    private String name;

    @Schema(
            description = "The location or address of the cinema hall",
            example = "221B Baker Street, London",
            maxLength = 200
    )
    @NotBlank(message = "Location must not be blank")
    @Size(max = 200, message = "Location must be at most 200 characters")
    private String location;

    @Schema(
            description = "Total seat capacity of the cinema hall",
            example = "150",
            minimum = "1"
    )
    @Min(value = 1, message = "Seat capacity must be at least 1")
    private int seatCapacity;
}