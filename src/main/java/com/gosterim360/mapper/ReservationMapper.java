package com.gosterim360.mapper;

import com.gosterim360.common.BaseMapper;
import com.gosterim360.dto.request.ReservationRequestDTO;
import com.gosterim360.dto.response.ReservationResponseDTO;
import com.gosterim360.model.Reservation;

public class ReservationMapper extends BaseMapper<Reservation, ReservationResponseDTO, ReservationRequestDTO> {

    @Override
    public ReservationResponseDTO toDTO(Reservation entity) {
        return ReservationResponseDTO.builder()
                .id(entity.getId())
                .sessionId(entity.getSessionId())
                .seatId(entity.getSeatId())
                .status(entity.getStatus())
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .build();
    }

    @Override
    public Reservation toEntity(ReservationRequestDTO request) {
        return Reservation.builder()
                .sessionId(request.getSessionId())
                .seatId(request.getSeatId())
                .status(request.getStatus())
                .build();
    }
}