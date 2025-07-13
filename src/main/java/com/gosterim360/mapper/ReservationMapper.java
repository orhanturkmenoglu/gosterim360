package com.gosterim360.mapper;

import com.gosterim360.common.BaseMapper;
import com.gosterim360.dto.request.ReservationRequestDTO;
import com.gosterim360.dto.response.ReservationResponseDTO;
import com.gosterim360.model.Reservation;
import com.gosterim360.model.Seat;
import com.gosterim360.model.Session;

public class ReservationMapper extends BaseMapper<Reservation, ReservationResponseDTO, ReservationRequestDTO> {

    @Override
    public ReservationResponseDTO toDTO(Reservation entity) {
        return ReservationResponseDTO.builder()
                .id(entity.getId())
                .sessionId(entity.getSession() != null ? entity.getSession().getId() : null)
                .seatId(entity.getSeat() != null ? entity.getSeat().getId() : null)
                .status(entity.getStatus())
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .build();
    }

    @Override
    public Reservation toEntity(ReservationRequestDTO request) {
        Session session = new Session();
        session.setId(request.getSessionId());

        Seat seat = new Seat();
        seat.setId(request.getSeatId());

        return Reservation.builder()
                .session(session)
                .seat(seat)
                .status(request.getStatus())
                .build();
    }
}