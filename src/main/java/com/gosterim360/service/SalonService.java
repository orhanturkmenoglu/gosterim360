package com.gosterim360.service;

import com.gosterim360.dto.request.SalonRequestDTO;
import com.gosterim360.dto.response.SalonResponseDTO;

import java.util.List;
import java.util.UUID;

public interface SalonService {
    SalonResponseDTO create(SalonRequestDTO request);

    SalonResponseDTO update(UUID id, SalonRequestDTO request);

    void delete(UUID id);

    SalonResponseDTO getById(UUID id);

    List<SalonResponseDTO> getAll();
}