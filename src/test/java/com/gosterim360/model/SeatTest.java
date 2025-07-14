package com.gosterim360.model;

import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class SeatTest {

    @Test
    void testSeatBuilderAndFields() {
        Salon salon = Salon.builder().name("Test Salon").build();
        int rowNumber = 3;
        int seatNumber = 7;
        List<Reservation> reservations = new ArrayList<>();

        Seat seat = Seat.builder()
                .salon(salon)
                .rowNumber(rowNumber)
                .seatNumber(seatNumber)
                .reservations(reservations)
                .build();

        assertEquals(salon, seat.getSalon());
        assertEquals(rowNumber, seat.getRowNumber());
        assertEquals(seatNumber, seat.getSeatNumber());
        assertEquals(reservations, seat.getReservations());
    }

    @Test
    void testSettersAndGetters() {
        Seat seat = new Seat();
        Salon salon = new Salon();
        seat.setSalon(salon);
        seat.setRowNumber(5);
        seat.setSeatNumber(10);
        List<Reservation> reservations = new ArrayList<>();
        seat.setReservations(reservations);

        assertEquals(salon, seat.getSalon());
        assertEquals(5, seat.getRowNumber());
        assertEquals(10, seat.getSeatNumber());
        assertEquals(reservations, seat.getReservations());
    }

    @Test
    void testReservationList() {
        Seat seat = new Seat();
        List<Reservation> reservations = new ArrayList<>();
        Reservation reservation = new Reservation();
        reservations.add(reservation);
        seat.setReservations(reservations);

        assertNotNull(seat.getReservations());
        assertEquals(1, seat.getReservations().size());
        assertEquals(reservation, seat.getReservations().get(0));
    }
}