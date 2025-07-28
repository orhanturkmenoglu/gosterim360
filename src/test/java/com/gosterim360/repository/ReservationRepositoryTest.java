package com.gosterim360.repository;

import com.gosterim360.enums.ReservationStatus;
import com.gosterim360.model.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class ReservationRepositoryTest {

    @Autowired
    private ReservationRepository reservationRepository;
    @Autowired
    private SessionRepository sessionRepository;
    @Autowired
    private SeatRepository seatRepository;
    @Autowired
    private MovieRepository movieRepository;
    @Autowired
    private SalonRepository salonRepository;

    private Movie createMovie(String name) {
        Movie movie = new Movie();
        movie.setName(name);
        movie.setGenre("Action");
        movie.setDuration(120.0);
        movie.setPosterUrl("http://example.com/poster.jpg");
        movie.setRating(8.5);
        movie.setReviewCount(100);
        movie.setDescription("Test description");
        return movieRepository.save(movie);
    }

    private Salon createSalon(String name) {
        Salon salon = new Salon();
        salon.setName(name);
        salon.setLocation("Test Location");
        salon.setSeatCapacity(100);
        return salonRepository.save(salon);
    }

    private Session createSession(String movieName) {
        Session session = new Session();
        session.setDate(LocalDate.now());
        session.setMovie(createMovie(movieName));
        return sessionRepository.save(session);
    }

    private Seat createSeat(String salonName, int row, int number) {
        Seat seat = new Seat();
        seat.setRowNumber(row);
        seat.setSeatNumber(number);
        seat.setSalon(createSalon(salonName));
        return seatRepository.save(seat);
    }

    @Test
    @DisplayName("findAllBySession_Id returns all reservations for a session")
    void findAllBySessionId_returnsReservations() {
        String unique = UUID.randomUUID().toString();
        Session session = createSession("Movie " + unique);
        Seat seat1 = createSeat("Salon1-" + unique, 1, 1);
        Seat seat2 = createSeat("Salon2-" + unique, 1, 2);

        Reservation reservation1 = Reservation.builder()
                .session(session)
                .seat(seat1)
                .status(ReservationStatus.PRE_RESERVED)
                .build();
        Reservation reservation2 = Reservation.builder()
                .session(session)
                .seat(seat2)
                .status(ReservationStatus.PAID)
                .build();
        reservationRepository.save(reservation1);
        reservationRepository.save(reservation2);

        List<Reservation> reservations = reservationRepository.findAllBySession_Id(session.getId());
        assertEquals(2, reservations.size());
        assertTrue(reservations.stream().anyMatch(r -> r.getSeat().getId().equals(seat1.getId())));
        assertTrue(reservations.stream().anyMatch(r -> r.getSeat().getId().equals(seat2.getId())));
    }

    @Test
    @DisplayName("existsBySession_IdAndSeat_Id returns true if reservation exists")
    void existsBySessionIdAndSeatId_returnsTrueIfExists() {
        String unique = UUID.randomUUID().toString();
        Session session = createSession("Movie " + unique);
        Seat seat = createSeat("Salon3-" + unique, 2, 1);

        Reservation reservation = Reservation.builder()
                .session(session)
                .seat(seat)
                .status(ReservationStatus.PAID)
                .build();
        reservationRepository.save(reservation);

        boolean exists = reservationRepository.existsBySession_IdAndSeat_Id(session.getId(), seat.getId());
        assertTrue(exists);
    }

    @Test
    @DisplayName("existsBySession_IdAndSeat_Id returns false if reservation does not exist")
    void existsBySessionIdAndSeatId_returnsFalseIfNotExists() {
        String unique = UUID.randomUUID().toString();
        Session session = createSession("Movie " + unique);
        Seat seat = createSeat("Salon4-" + unique, 3, 1);

        boolean exists = reservationRepository.existsBySession_IdAndSeat_Id(session.getId(), seat.getId());
        assertFalse(exists);
    }

    @Test
    @DisplayName("CRUD operations for Reservation")
    void reservationCrudOperations() {
        String unique = UUID.randomUUID().toString();
        Session session = createSession("Movie " + unique);
        Seat seat = createSeat("Salon5-" + unique, 4, 1);

        Reservation reservation = Reservation.builder()
                .session(session)
                .seat(seat)
                .status(ReservationStatus.PRE_RESERVED)
                .build();
        reservation = reservationRepository.save(reservation);
        assertNotNull(reservation.getId());

        Reservation found = reservationRepository.findById(reservation.getId()).orElse(null);
        assertNotNull(found);
        assertEquals(session.getId(), found.getSession().getId());
        assertEquals(seat.getId(), found.getSeat().getId());

        reservation.setStatus(ReservationStatus.PAID);
        reservationRepository.save(reservation);
        Reservation updated = reservationRepository.findById(reservation.getId()).orElse(null);
        assertEquals(ReservationStatus.PAID, updated.getStatus());

        reservationRepository.delete(reservation);
        assertFalse(reservationRepository.findById(reservation.getId()).isPresent());
    }
}