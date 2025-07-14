package com.gosterim360.model;

import com.gosterim360.enums.ReservationStatus;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ReservationTest {

    @Test
    @DisplayName("Builder sets all fields correctly with PRE_RESERVED status")
    void builderSetsFieldsWithPreReservedStatus() {
        Session session = new Session();
        Seat seat = new Seat();
        ReservationStatus status = ReservationStatus.PRE_RESERVED;

        Reservation reservation = Reservation.builder()
                .session(session)
                .seat(seat)
                .status(status)
                .build();

        assertEquals(session, reservation.getSession());
        assertEquals(seat, reservation.getSeat());
        assertEquals(ReservationStatus.PRE_RESERVED, reservation.getStatus());
    }

    @Test
    @DisplayName("Builder sets all fields correctly with PAID status")
    void builderSetsFieldsWithPaidStatus() {
        Session session = new Session();
        Seat seat = new Seat();
        ReservationStatus status = ReservationStatus.PAID;

        Reservation reservation = Reservation.builder()
                .session(session)
                .seat(seat)
                .status(status)
                .build();

        assertEquals(session, reservation.getSession());
        assertEquals(seat, reservation.getSeat());
        assertEquals(ReservationStatus.PAID, reservation.getStatus());
    }

    @Test
    @DisplayName("Setters and getters work for all fields and statuses")
    void settersAndGettersWorkForAllStatuses() {
        Reservation reservation = new Reservation();
        Session session = new Session();
        Seat seat = new Seat();

        reservation.setSession(session);
        reservation.setSeat(seat);
        reservation.setStatus(ReservationStatus.PRE_RESERVED);
        assertEquals(ReservationStatus.PRE_RESERVED, reservation.getStatus());

        reservation.setStatus(ReservationStatus.PAID);
        assertEquals(ReservationStatus.PAID, reservation.getStatus());
        assertEquals(session, reservation.getSession());
        assertEquals(seat, reservation.getSeat());
    }
}