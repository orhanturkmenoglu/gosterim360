package com.gosterim360.model;


import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "movies")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Movie {

    @Id
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


    @PrePersist
    public void onCreate() {
        this.id = UUID.randomUUID();
        this.createdAt = Instant.now();
        this.updatedAt = Instant.now();
    }


    @PreUpdate
    public void onUpdate() {
        this.updatedAt = Instant.now();
    }
}
