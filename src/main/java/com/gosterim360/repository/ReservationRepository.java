package com.gosterim360.repository;

import com.gosterim360.model.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;
import java.util.Optional;
import java.util.List;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, UUID> {
    Optional<Reservation> findBySessionIdAndSeatId(UUID sessionId, UUID seatId);
    List<Reservation> findAllBySessionId(UUID sessionId);
    boolean existsBySessionIdAndSeatId(UUID sessionId, UUID seatId);
}