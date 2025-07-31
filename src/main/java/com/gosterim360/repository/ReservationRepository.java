package com.gosterim360.repository;

import com.gosterim360.model.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.UUID;
import java.util.List;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, UUID> {

    List<Reservation> findAllBySession_Id(UUID sessionId);

    boolean existsBySession_IdAndSeat_Id(UUID sessionId, UUID seatId);

    @Query("select r from Reservation  r where r.status=='PRE_RESERVED' and r.createdAt<=:timeoutThreshold")
    List<Reservation> findExpiredPreReservedReservations(@Param("timeoutThreshold")Instant timeoutThreshold);
}