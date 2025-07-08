package com.gosterim360.dto.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class MovieRequestDTO {

    @NotEmpty(message = "Name is required")
    private String name;

    @NotEmpty(message = "Description is required")
    private String description;

    @NotEmpty(message = "Genre is required")
    private String genre;

    @NotNull(message = "Director is required")
    @Positive(message = "Director must be a positive number")
    private Double duration;

    @NotNull(message = "Rating is required")
    @Positive(message = "Rating must be a positive number")
    private Double rating;

    @NotNull(message = "Review count is required")
    private Integer reviewCount;

    @NotEmpty(message = "Poster URL is required")
    private String posterUrl;
}
