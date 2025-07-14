package com.gosterim360.model;

import com.gosterim360.common.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "salon")
public class Salon extends BaseEntity<UUID> {

    @Column(nullable = false, length = 100, unique = true)
    private String name;

    @Column(nullable = false, length = 200)
    private String location;

    @Column(name = "seat_capacity", nullable = false)
    private int seatCapacity;

    @OneToMany(mappedBy = "salon", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Seat> seats;
}