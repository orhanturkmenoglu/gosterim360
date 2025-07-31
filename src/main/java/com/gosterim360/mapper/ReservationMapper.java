package com.gosterim360.mapper;

import com.gosterim360.common.BaseMapper;
import com.gosterim360.dto.request.ReservationRequestDTO;
import com.gosterim360.dto.response.ReservationResponseDTO;
import com.gosterim360.dto.response.SeatResponseDTO;
import com.gosterim360.dto.response.SessionResponseDTO;
import com.gosterim360.model.Reservation;
import com.gosterim360.model.Seat;
import com.gosterim360.model.Session;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ReservationMapper extends BaseMapper<Reservation, ReservationResponseDTO, ReservationRequestDTO> {

    private final SessionMapper sessionMapper;
    private final SeatMapper seatMapper;

    @Override
    public ReservationResponseDTO toDTO(Reservation entity) {

        SessionResponseDTO sessionDTO = null;
        SeatResponseDTO seatDTO = null;

        if (entity.getSession() != null) {
            sessionDTO = sessionMapper.toDTO(entity.getSession());
        }
        if (entity.getSeat() != null) {
            seatDTO = seatMapper.toDTO(entity.getSeat());
        }


        return ReservationResponseDTO.builder()
                .id(entity.getId())
                .session(sessionDTO)
                .seat(seatDTO)
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