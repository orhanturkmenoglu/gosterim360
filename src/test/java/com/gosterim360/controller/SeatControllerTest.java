/*
package com.gosterim360.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gosterim360.common.BaseResponse;
import com.gosterim360.dto.request.SeatRequestDTO;
import com.gosterim360.dto.response.SeatResponseDTO;
import com.gosterim360.service.SeatService;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.UUID;

import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(SeatController.class)
class SeatControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private SeatService seatService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("Create seat - success")
    void createSeat_success() throws Exception {
        SeatRequestDTO request = SeatRequestDTO.builder()
                .salonId("1")
                .rowNumber(5)
                .seatNumber(8)
                .build();

        SeatResponseDTO response = SeatResponseDTO.builder()
                .id(UUID.randomUUID().toString())
                .salonId("1")
                .rowNumber(5)
                .seatNumber(8)
                .build();

        Mockito.when(seatService.createSeat(any(SeatRequestDTO.class))).thenReturn(response);

        mockMvc.perform(post("/api/v1/seats")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success", is(true)))
                .andExpect(jsonPath("$.data.id", is(response.getId())))
                .andExpect(jsonPath("$.data.rowNumber", is(5)))
                .andExpect(jsonPath("$.data.seatNumber", is(8)));
    }

    @Test
    @DisplayName("Get seat by ID - success")
    void getSeatById_success() throws Exception {
        String seatId = UUID.randomUUID().toString();
        SeatResponseDTO response = SeatResponseDTO.builder()
                .id(seatId)
                .salonId("1")
                .rowNumber(3)
                .seatNumber(7)
                .build();

        Mockito.when(seatService.getSeatById(eq(UUID.fromString(seatId)))).thenReturn(response);

        mockMvc.perform(get("/api/v1/seats/{id}", seatId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success", is(true)))
                .andExpect(jsonPath("$.data.id", is(seatId)))
                .andExpect(jsonPath("$.data.rowNumber", is(3)))
                .andExpect(jsonPath("$.data.seatNumber", is(7)));
    }

    @Test
    @DisplayName("Get all seats - success")
    void getAllSeats_success() throws Exception {
        SeatResponseDTO seat1 = SeatResponseDTO.builder()
                .id(UUID.randomUUID().toString())
                .salonId("1")
                .rowNumber(1)
                .seatNumber(1)
                .build();
        SeatResponseDTO seat2 = SeatResponseDTO.builder()
                .id(UUID.randomUUID().toString())
                .salonId("1")
                .rowNumber(1)
                .seatNumber(2)
                .build();

        Mockito.when(seatService.getAllSeats()).thenReturn(List.of(seat1, seat2));

        mockMvc.perform(get("/api/v1/seats"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success", is(true)))
                .andExpect(jsonPath("$.data", hasSize(2)))
                .andExpect(jsonPath("$.data[0].seatNumber", is(1)))
                .andExpect(jsonPath("$.data[1].seatNumber", is(2)));
    }

    @Test
    @DisplayName("Update seat - success")
    void updateSeat_success() throws Exception {
        String seatId = UUID.randomUUID().toString();
        SeatRequestDTO request = SeatRequestDTO.builder()
                .salonId("1")
                .rowNumber(2)
                .seatNumber(3)
                .build();

        SeatResponseDTO response = SeatResponseDTO.builder()
                .id(seatId)
                .salonId("1")
                .rowNumber(2)
                .seatNumber(3)
                .build();

        Mockito.when(seatService.updateSeat(eq(UUID.fromString(seatId)), any(SeatRequestDTO.class))).thenReturn(response);

        mockMvc.perform(put("/api/v1/seats/{id}", seatId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success", is(true)))
                .andExpect(jsonPath("$.data.id", is(seatId)))
                .andExpect(jsonPath("$.data.rowNumber", is(2)))
                .andExpect(jsonPath("$.data.seatNumber", is(3)));
    }

    @Test
    @DisplayName("Delete seat - success")
    void deleteSeat_success() throws Exception {
        String seatId = UUID.randomUUID().toString();

        Mockito.doNothing().when(seatService).deleteSeat(UUID.fromString(seatId));

        mockMvc.perform(delete("/api/v1/seats/{id}", seatId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success", is(true)))
                .andExpect(jsonPath("$.message", containsString("deleted")));
    }

    @Test
    @DisplayName("Create seat - validation error")
    void createSeat_validationError() throws Exception {
        SeatRequestDTO request = SeatRequestDTO.builder()
                .salonId(null)
                .rowNumber(0)
                .seatNumber(0)
                .build();

        mockMvc.perform(post("/api/v1/seats")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }
}*/
