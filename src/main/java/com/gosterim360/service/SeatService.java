package com.gosterim360.service;

import com.gosterim360.dto.request.SeatRequestDTO;
import com.gosterim360.dto.response.SeatResponseDTO;

import java.util.List;
import java.util.UUID;

public interface SeatService {
    SeatResponseDTO createSeat(SeatRequestDTO request);

    SeatResponseDTO getSeatById(UUID id);

    List<SeatResponseDTO> getAllSeats();

    SeatResponseDTO updateSeat(UUID id, SeatRequestDTO request);

    void deleteSeat(UUID id);
}