package com.gosterim360.model;


import com.gosterim360.common.BaseEntity;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.*;

import java.util.List;
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

    @OneToMany(mappedBy = "movie",cascade = CascadeType.ALL,orphanRemoval = true)
    private List<Session> sessions;
}
