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
@Schema(name = "MovieResponseDTO", description = "Response DTO representing a movie with all details")
public class MovieResponseDTO {

    @Schema(description = "Unique identifier of the movie", example = "c7f2f8c5-5f25-4d63-bd52-3c5c97e47c1a")
    private UUID id;

    @Schema(description = "Name of the movie", example = "Inception")
    private String name;

    @Schema(description = "Detailed description of the movie", example = "A mind-bending thriller about dreams within dreams.")
    private String description;

    @Schema(description = "Genre of the movie", example = "Sci-Fi")
    private String genre;

    @Schema(description = "Duration of the movie in minutes", example = "148.0")
    private Double duration;

    @Schema(description = "Rating of the movie", example = "8.7")
    private Double rating;

    @Schema(description = "Number of reviews the movie has received", example = "5000")
    private Integer reviewCount;

    @Schema(description = "URL of the movie poster image", example = "https://example.com/poster.jpg")
    private String posterUrl;

    @Schema(description = "Creation timestamp", example = "2025-07-08T10:30:00Z")
    private Instant createdAt;

    @Schema(description = "Last update timestamp", example = "2025-07-09T11:15:00Z")
    private Instant updatedAt;
}
