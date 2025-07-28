/*
package com.gosterim360.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gosterim360.dto.request.ReservationRequestDTO;
import com.gosterim360.dto.response.ReservationResponseDTO;
import com.gosterim360.enums.ReservationStatus;
import com.gosterim360.service.ReservationService;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ReservationController.class)
class ReservationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ReservationService reservationService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("Create reservation - success")
    void createReservation_success() throws Exception {
        UUID sessionId = UUID.randomUUID();
        UUID seatId = UUID.randomUUID();
        UUID reservationId = UUID.randomUUID();

        ReservationRequestDTO request = ReservationRequestDTO.builder()
                .sessionId(sessionId)
                .seatId(seatId)
                .status(ReservationStatus.PRE_RESERVED)
                .build();

        ReservationResponseDTO response = ReservationResponseDTO.builder()
                .id(reservationId)
                .sessionId(sessionId)
                .seatId(seatId)
                .status(ReservationStatus.PRE_RESERVED)
                .createdAt(Instant.now())
                .updatedAt(Instant.now())
                .build();

        Mockito.when(reservationService.create(any(ReservationRequestDTO.class))).thenReturn(response);

        mockMvc.perform(post("/api/v1/reservations")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.success", is(true)))
                .andExpect(jsonPath("$.data.id", is(reservationId.toString())))
                .andExpect(jsonPath("$.data.sessionId", is(sessionId.toString())))
                .andExpect(jsonPath("$.data.seatId", is(seatId.toString())))
                .andExpect(jsonPath("$.data.status", is("PRE_RESERVED")));
    }

    @Test
    @DisplayName("Update reservation - success")
    void updateReservation_success() throws Exception {
        UUID sessionId = UUID.randomUUID();
        UUID seatId = UUID.randomUUID();
        UUID reservationId = UUID.randomUUID();

        ReservationRequestDTO request = ReservationRequestDTO.builder()
                .sessionId(sessionId)
                .seatId(seatId)
                .status(ReservationStatus.PAID)
                .build();

        ReservationResponseDTO response = ReservationResponseDTO.builder()
                .id(reservationId)
                .sessionId(sessionId)
                .seatId(seatId)
                .status(ReservationStatus.PAID)
                .createdAt(Instant.now())
                .updatedAt(Instant.now())
                .build();

        Mockito.when(reservationService.update(eq(reservationId), any(ReservationRequestDTO.class))).thenReturn(response);

        mockMvc.perform(put("/api/v1/reservations/{id}", reservationId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success", is(true)))
                .andExpect(jsonPath("$.data.id", is(reservationId.toString())))
                .andExpect(jsonPath("$.data.status", is("PAID")));
    }

    @Test
    @DisplayName("Delete reservation - success")
    void deleteReservation_success() throws Exception {
        UUID reservationId = UUID.randomUUID();

        Mockito.doNothing().when(reservationService).delete(reservationId);

        mockMvc.perform(delete("/api/v1/reservations/{id}", reservationId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success", is(true)))
                .andExpect(jsonPath("$.message", containsString("deleted")));
    }

    @Test
    @DisplayName("Get reservation by ID - success")
    void getReservationById_success() throws Exception {
        UUID sessionId = UUID.randomUUID();
        UUID seatId = UUID.randomUUID();
        UUID reservationId = UUID.randomUUID();

        ReservationResponseDTO response = ReservationResponseDTO.builder()
                .id(reservationId)
                .sessionId(sessionId)
                .seatId(seatId)
                .status(ReservationStatus.PAID)
                .createdAt(Instant.now())
                .updatedAt(Instant.now())
                .build();

        Mockito.when(reservationService.getById(reservationId)).thenReturn(response);

        mockMvc.perform(get("/api/v1/reservations/{id}", reservationId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success", is(true)))
                .andExpect(jsonPath("$.data.id", is(reservationId.toString())))
                .andExpect(jsonPath("$.data.status", is("PAID")));
    }

    @Test
    @DisplayName("Get all reservations - success")
    void getAllReservations_success() throws Exception {
        UUID sessionId = UUID.randomUUID();
        UUID seatId = UUID.randomUUID();
        UUID reservationId = UUID.randomUUID();

        ReservationResponseDTO response = ReservationResponseDTO.builder()
                .id(reservationId)
                .sessionId(sessionId)
                .seatId(seatId)
                .status(ReservationStatus.PRE_RESERVED)
                .createdAt(Instant.now())
                .updatedAt(Instant.now())
                .build();

        Mockito.when(reservationService.getAll()).thenReturn(List.of(response));

        mockMvc.perform(get("/api/v1/reservations"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success", is(true)))
                .andExpect(jsonPath("$.data", hasSize(1)))
                .andExpect(jsonPath("$.data[0].id", is(reservationId.toString())));
    }

    @Test
    @DisplayName("Get reservations by sessionId - success")
    void getReservationsBySessionId_success() throws Exception {
        UUID sessionId = UUID.randomUUID();
        UUID seatId = UUID.randomUUID();
        UUID reservationId = UUID.randomUUID();

        ReservationResponseDTO response = ReservationResponseDTO.builder()
                .id(reservationId)
                .sessionId(sessionId)
                .seatId(seatId)
                .status(ReservationStatus.PRE_RESERVED)
                .createdAt(Instant.now())
                .updatedAt(Instant.now())
                .build();

        Mockito.when(reservationService.getBySessionId(sessionId)).thenReturn(List.of(response));

        mockMvc.perform(get("/api/v1/reservations/session/{sessionId}", sessionId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success", is(true)))
                .andExpect(jsonPath("$.data", hasSize(1)))
                .andExpect(jsonPath("$.data[0].sessionId", is(sessionId.toString())));
    }

    @Test
    @DisplayName("Create reservation - validation error")
    void createReservation_validationError() throws Exception {
        ReservationRequestDTO request = ReservationRequestDTO.builder()
                .sessionId(null)
                .seatId(null)
                .status(null)
                .build();

        mockMvc.perform(post("/api/v1/reservations")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }
}*/
