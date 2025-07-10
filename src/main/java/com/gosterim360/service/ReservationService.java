package com.gosterim360.service;

import com.gosterim360.dto.request.ReservationRequestDTO;
import com.gosterim360.dto.response.ReservationResponseDTO;

import java.util.List;
import java.util.UUID;

public interface ReservationService {
    ReservationResponseDTO create(ReservationRequestDTO request);

    ReservationResponseDTO update(UUID id, ReservationRequestDTO request);

    void delete(UUID id);

    ReservationResponseDTO getById(UUID id);

    List<ReservationResponseDTO> getAll();

    List<ReservationResponseDTO> getBySessionId(UUID sessionId);
}