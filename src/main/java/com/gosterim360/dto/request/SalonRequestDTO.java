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
            description = "Name of the cinema hall",
            example = "Caspian Cinema Hall",
            maxLength = 100,
            required = true
    )
    @NotBlank(message = "Name must not be blank")
    @Size(max = 100, message = "Name must be at most 100 characters")
    private String isim;

    @Schema(
            description = "Location/address of the cinema hall",
            example = "Baku, Nizami street 123",
            maxLength = 200,
            required = true
    )
    @NotBlank(message = "Location must not be blank")
    @Size(max = 200, message = "Location must be at most 200 characters")
    private String lokasyon;

    @Schema(
            description = "Total seat capacity of the hall",
            example = "150",
            minimum = "1",
            required = true
    )
    @Min(value = 1, message = "Seat capacity must be at least 1")
    private int koltukKapasitesi;
}