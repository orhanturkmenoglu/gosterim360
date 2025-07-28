/*
package com.gosterim360.service;

import com.gosterim360.dto.request.ReservationRequestDTO;
import com.gosterim360.dto.response.ReservationResponseDTO;
import com.gosterim360.enums.ReservationStatus;
import com.gosterim360.model.Reservation;
import com.gosterim360.model.Seat;
import com.gosterim360.model.Session;
import com.gosterim360.repository.ReservationRepository;
import com.gosterim360.repository.SeatRepository;
import com.gosterim360.repository.SessionRepository;
import com.gosterim360.service.impl.ReservationServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

class ReservationServiceImplTest {

    @Mock
    private ReservationRepository reservationRepository;
    @Mock
    private SessionRepository sessionRepository;
    @Mock
    private SeatRepository seatRepository;

    @InjectMocks
    private ReservationServiceImpl reservationService;

    private UUID reservationId;
    private UUID sessionId;
    private UUID seatId;
    private Session session;
    private Seat seat;
    private Reservation reservation;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        reservationId = UUID.randomUUID();
        sessionId = UUID.randomUUID();
        seatId = UUID.randomUUID();

        session = new Session();
        session.setId(sessionId);

        seat = new Seat();
        seat.setId(seatId);

        reservation = Reservation.builder()
                .session(session)
                .seat(seat)
                .status(ReservationStatus.PRE_RESERVED)
                .build();
    }

    @Test
    @DisplayName("Create reservation - success")
    void createReservation_success() {
        ReservationRequestDTO request = ReservationRequestDTO.builder()
                .sessionId(sessionId)
                .seatId(seatId)
                .status(ReservationStatus.PRE_RESERVED)
                .build();

        when(sessionRepository.findById(sessionId)).thenReturn(Optional.of(session));
        when(seatRepository.findById(seatId)).thenReturn(Optional.of(seat));
        when(reservationRepository.save(any(Reservation.class))).thenReturn(reservation);

        ReservationResponseDTO response = reservationService.create(request);

        assertNotNull(response);
        assertEquals(reservationId, response.getId());
        assertEquals(sessionId, response.getSession().getId());

        assertEquals(seatId, response.getSeat().getId());
        assertEquals(ReservationStatus.PRE_RESERVED, response.getStatus());
    }

    @Test
    @DisplayName("Update reservation - success")
    void updateReservation_success() {
        ReservationRequestDTO request = ReservationRequestDTO.builder()
                .sessionId(sessionId)
                .seatId(seatId)
                .status(ReservationStatus.PAID)
                .build();

        when(reservationRepository.findById(reservationId)).thenReturn(Optional.of(reservation));
        when(sessionRepository.findById(sessionId)).thenReturn(Optional.of(session));
        when(seatRepository.findById(seatId)).thenReturn(Optional.of(seat));
        when(reservationRepository.save(any(Reservation.class))).thenReturn(reservation);

        ReservationResponseDTO response = reservationService.update(reservationId, request);

        assertNotNull(response);
        assertEquals(reservationId, response.getId());
        assertEquals(ReservationStatus.PAID, response.getStatus());
    }

    @Test
    @DisplayName("Delete reservation - success")
    void deleteReservation_success() {
        when(reservationRepository.existsById(reservationId)).thenReturn(true);
        doNothing().when(reservationRepository).deleteById(reservationId);

        assertDoesNotThrow(() -> reservationService.delete(reservationId));
        verify(reservationRepository, times(1)).deleteById(reservationId);
    }

    @Test
    @DisplayName("Get reservation by ID - success")
    void getReservationById_success() {
        when(reservationRepository.findById(reservationId)).thenReturn(Optional.of(reservation));

        ReservationResponseDTO response = reservationService.getById(reservationId);

        assertNotNull(response);
        assertEquals(reservationId, response.getId());
    }

    @Test
    @DisplayName("Get all reservations - success")
    void getAllReservations_success() {
        when(reservationRepository.findAll()).thenReturn(List.of(reservation));

        List<ReservationResponseDTO> responses = reservationService.getAll();

        assertNotNull(responses);
        assertEquals(1, responses.size());
        assertEquals(reservationId, responses.get(0).getId());
    }

    @Test
    @DisplayName("Get reservations by sessionId - success")
    void getReservationsBySessionId_success() {
        when(reservationRepository.findAllBySession_Id(sessionId)).thenReturn(List.of(reservation));

        List<ReservationResponseDTO> responses = reservationService.getBySessionId(sessionId);

        assertNotNull(responses);
        assertEquals(1, responses.size());
        assertEquals(sessionId, responses.get(0).getSession().getId());
    }
}*/
