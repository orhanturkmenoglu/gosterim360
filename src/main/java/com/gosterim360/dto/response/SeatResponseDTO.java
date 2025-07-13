package com.gosterim360.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SeatResponseDTO {

    @Schema(description = "Unique identifier of the seat", example = "a1b2c3d4-e5f6-7890-abcd-1234567890ef")
    private String id;

    @Schema(description = "ID of the salon to which the seat belongs", example = "1")
    private String salonId;

    @Schema(description = "Row number of the seat", example = "5")
    private Integer rowNumber;

    @Schema(description = "Seat number within the row", example = "8")
    private Integer seatNumber;
}