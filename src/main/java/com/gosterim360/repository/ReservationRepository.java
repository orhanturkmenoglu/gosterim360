package com.gosterim360.repository;

import com.gosterim360.model.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.UUID;
import java.util.List;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, UUID> {

    List<Reservation> findAllBySession_Id(UUID sessionId);

    boolean existsBySession_IdAndSeat_Id(UUID sessionId, UUID seatId);
}