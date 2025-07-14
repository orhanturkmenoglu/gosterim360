package com.gosterim360.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gosterim360.dto.request.SalonRequestDTO;
import com.gosterim360.dto.response.SalonResponseDTO;
import com.gosterim360.service.SalonService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.Instant;
import java.util.*;

import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(SalonController.class)
class SalonControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private SalonService salonService;

    @Autowired
    private ObjectMapper objectMapper;

    // Helper method to create SalonResponseDTO
    private SalonResponseDTO createSalonResponseDTO(UUID id, String name, String location, Integer capacity, Instant createdAt, Instant updatedAt) {
        return SalonResponseDTO.builder()
                .id(id)
                .name(name)
                .location(location)
                .seatCapacity(capacity)
                .createdAt(createdAt)
                .updatedAt(updatedAt)
                .build();
    }

    @Test
    @DisplayName("POST /api/v1/salons - success")
    void createSalon_success() throws Exception {
        SalonRequestDTO request = SalonRequestDTO.builder().name("Hall 1").location("Baku").seatCapacity(100).build();
        SalonResponseDTO response = createSalonResponseDTO(
                UUID.randomUUID(),
                "Hall 1",
                "Baku",
                100,
                Instant.now(),
                Instant.now()
        );

        Mockito.when(salonService.create(any())).thenReturn(response);

        mockMvc.perform(post("/api/v1/salons")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.data.name").value("Hall 1"))
                .andExpect(jsonPath("$.data.seatCapacity").value(100))
                .andExpect(jsonPath("$.message").value("Cinema hall created successfully"));
    }

    @Test
    @DisplayName("PUT /api/v1/salons/{id} - success")
    void updateSalon_success() throws Exception {
        UUID id = UUID.randomUUID();
        SalonRequestDTO request = SalonRequestDTO.builder().name("Updated Hall").location("Baku").seatCapacity(120).build();
        SalonResponseDTO response = createSalonResponseDTO(
                id,
                "Updated Hall",
                "Baku",
                120,
                Instant.now(),
                Instant.now()
        );

        Mockito.when(salonService.update(eq(id), any())).thenReturn(response);

        mockMvc.perform(put("/api/v1/salons/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.name").value("Updated Hall"))
                .andExpect(jsonPath("$.data.seatCapacity").value(120))
                .andExpect(jsonPath("$.message").value("Cinema hall updated successfully"));
    }

    @Test
    @DisplayName("DELETE /api/v1/salons/{id} - success")
    void deleteSalon_success() throws Exception {
        UUID id = UUID.randomUUID();

        mockMvc.perform(delete("/api/v1/salons/{id}", id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Cinema hall deleted successfully"));
        Mockito.verify(salonService).delete(id);
    }

    @Test
    @DisplayName("GET /api/v1/salons/{id} - success")
    void getSalonById_success() throws Exception {
        UUID id = UUID.randomUUID();
        SalonResponseDTO response = createSalonResponseDTO(
                id,
                "Hall 1",
                "Baku",
                100,
                Instant.now(),
                Instant.now()
        );

        Mockito.when(salonService.getById(id)).thenReturn(response);

        mockMvc.perform(get("/api/v1/salons/{id}", id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.name").value("Hall 1"))
                .andExpect(jsonPath("$.data.seatCapacity").value(100))
                .andExpect(jsonPath("$.message").value("Cinema hall fetched successfully"));
    }

    @Test
    @DisplayName("GET /api/v1/salons - success, non-empty list")
    void getAllSalons_success() throws Exception {
        SalonResponseDTO response = createSalonResponseDTO(
                UUID.randomUUID(),
                "Hall 1",
                "Baku",
                100,
                Instant.now(),
                Instant.now()
        );
        List<SalonResponseDTO> list = Collections.singletonList(response);

        Mockito.when(salonService.getAll()).thenReturn(list);

        mockMvc.perform(get("/api/v1/salons"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data", hasSize(1)))
                .andExpect(jsonPath("$.data[0].name").value("Hall 1"))
                .andExpect(jsonPath("$.message").value("Cinema halls fetched successfully"));
    }

    @Test
    @DisplayName("GET /api/v1/salons - success, empty list")
    void getAllSalons_emptyList() throws Exception {
        Mockito.when(salonService.getAll()).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/api/v1/salons"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data", hasSize(0)))
                .andExpect(jsonPath("$.message").value("Cinema halls fetched successfully"));
    }

    // --- VALIDATION TESTS ---

    @Test
    @DisplayName("POST /api/v1/salons - validation error (empty name, negative capacity)")
    void createSalon_validationError() throws Exception {
        SalonRequestDTO request = SalonRequestDTO.builder().name("").location("").seatCapacity(-1).build();

        mockMvc.perform(post("/api/v1/salons")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("PUT /api/v1/salons/{id} - validation error (empty name, negative capacity)")
    void updateSalon_validationError() throws Exception {
        UUID id = UUID.randomUUID();
        SalonRequestDTO request = SalonRequestDTO.builder().name("").location("").seatCapacity(-1).build();

        mockMvc.perform(put("/api/v1/salons/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }
}