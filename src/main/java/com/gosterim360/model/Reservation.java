package com.gosterim360.model;

import com.gosterim360.common.BaseEntity;
import com.gosterim360.enums.ReservationStatus;
import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "reservation")
public class Reservation extends BaseEntity<UUID> {

    @Column(name = "session_id", nullable = false)
    private UUID sessionId;

    @Column(name = "seat_id", nullable = false)
    private UUID seatId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private ReservationStatus status;
}