package com.gosterim360.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@Schema(name = "MovieRequestDTO", description = "Request DTO for creating or updating a movie")
public class MovieRequestDTO {

    @Schema(description = "Movie name", example = "Inception", required = true)
    @NotEmpty(message = "Name is required")
    private String name;

    @Schema(description = "Movie description", example = "A mind-bending thriller", required = true)
    @NotEmpty(message = "Description is required")
    private String description;

    @Schema(description = "Movie genre", example = "Sci-Fi", required = true)
    @NotEmpty(message = "Genre is required")
    private String genre;

    @Schema(description = "Duration in minutes", example = "120.00", required = true)
    @NotNull(message = "Duration is required")
    @Positive(message = "Duration must be a positive number")
    private Double duration;

    @Schema(description = "Rating of the movie", example = "7.8", required = true)
    @NotNull(message = "Rating is required")
    @Positive(message = "Rating must be a positive number")
    private Double rating;

    @Schema(description = "Number of reviews", example = "5000", required = true)
    @NotNull(message = "Review count is required")
    private Integer reviewCount;

    @Schema(description = "URL to the movie poster", example = "https://example.com/poster.jpg", required = true)
    @NotEmpty(message = "Poster URL is required")
    private String posterUrl;
}
