package com.gosterim360.model;


import com.gosterim360.common.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;

import java.util.UUID;

@Entity
@Table(name = "movies")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Movie extends BaseEntity<UUID> {

    private String name;

    private String description;

    private String genre;

    private Double duration;

    private Double rating;

    private Integer reviewCount;

    private String posterUrl;
}
