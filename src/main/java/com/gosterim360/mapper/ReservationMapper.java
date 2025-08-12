package com.gosterim360.mapper;

import com.gosterim360.common.BaseMapper;
import com.gosterim360.dto.request.ReservationRequestDTO;
import com.gosterim360.dto.response.ReservationResponseDTO;
import com.gosterim360.dto.response.SeatResponseDTO;
import com.gosterim360.dto.response.SessionResponseDTO;
import com.gosterim360.enums.ReservationStatus;
import com.gosterim360.model.Reservation;
import com.gosterim360.model.Seat;
import com.gosterim360.model.Session;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class ReservationMapper extends BaseMapper<Reservation, ReservationResponseDTO, ReservationRequestDTO> {

    private final SessionMapper sessionMapper;
    private final SeatMapper seatMapper;

    @Override
    public ReservationResponseDTO toDTO(Reservation entity) {

        SessionResponseDTO sessionDTO = null;
        SeatResponseDTO seatDTO = null;

        if (entity.getSession() != null) {
            sessionDTO = sessionMapper.toDTO(entity.getSession());
            log.info("ReservationResponseDTO::entity.getSession() != null :",sessionDTO);
        }
        if (entity.getSeat() != null) {
            seatDTO = seatMapper.toDTO(entity.getSeat());
            log.info("ReservationResponseDTO::entity.getSeat() != null:",seatDTO);
        }

        return ReservationResponseDTO.builder()
                .id(entity.getId())
                .session(sessionDTO)
                .seat(seatDTO)
                .status(entity.getStatus())
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .message("Lütfen ödemeyi tamamlamak için  bağlantıya tıklayın.")
                .build();
    }

    @Override
    public Reservation toEntity(ReservationRequestDTO request) {

        return Reservation.builder()
                .status(ReservationStatus.PRE_RESERVED)
                .build();
    }

}