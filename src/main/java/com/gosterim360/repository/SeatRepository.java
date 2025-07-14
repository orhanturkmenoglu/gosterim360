package com.gosterim360.repository;

import com.gosterim360.model.Seat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface SeatRepository extends JpaRepository<Seat, UUID> {

    boolean existsBySalonIdAndRowNumberAndSeatNumber(UUID salonId, int rowNumber, int seatNumber);
}