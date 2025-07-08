package com.gosterim360.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.util.UUID;

@Getter
@Setter
@Builder
public class MovieResponseDTO {

    private UUID id;

    private String name;

    private String description;

    private String genre;

    private Double duration;

    private Double rating;

    private Integer reviewCount;

    private String posterUrl;

    private Instant createdAt;
    private Instant updatedAt;
}
