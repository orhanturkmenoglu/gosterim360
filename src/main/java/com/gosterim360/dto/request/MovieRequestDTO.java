package com.gosterim360.dto.request;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class MovieRequestDTO {

    private String name;

    private String description;

    private String genre;

    private Double duration;

    private Double rating;

    private Integer reviewCount;

    private String posterUrl;
}
