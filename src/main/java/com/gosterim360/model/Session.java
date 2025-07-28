package com.gosterim360.model;

import com.gosterim360.common.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "sessions")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Session extends BaseEntity<UUID> {

    @Column(nullable = false)
    private LocalDate date;

    @OneToMany(mappedBy = "session",cascade = CascadeType.ALL,orphanRemoval = true,fetch = FetchType.LAZY)
    private List<SessionTime> times = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "movie_id",nullable = false)
    private  Movie movie;

    @ManyToOne
    @JoinColumn(name = "salon_id",nullable = false)
    private Salon salon;  // her gösterimin bir salonu olmalıdır.
}
