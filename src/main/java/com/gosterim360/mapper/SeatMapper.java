package com.gosterim360.mapper;

import com.gosterim360.dto.request.SeatRequestDTO;
import com.gosterim360.dto.response.SeatResponseDTO;
import com.gosterim360.model.Salon;
import com.gosterim360.common.BaseMapper;
import com.gosterim360.model.Seat;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class SeatMapper extends BaseMapper<Seat, SeatResponseDTO, SeatRequestDTO> {

    @Override
    public SeatResponseDTO toDTO(Seat entity) {
        if (entity == null) return null;
        return SeatResponseDTO.builder()
                .id(entity.getId() != null ? entity.getId().toString() : null)
                .salonId(entity.getSalon() != null && entity.getSalon().getId() != null ? entity.getSalon().getId().toString() : null)
                .rowNumber(entity.getRowNumber())
                .seatNumber(entity.getSeatNumber())
                .build();
    }

    @Override
    public Seat toEntity(SeatRequestDTO request) {
        if (request == null) return null;
        Salon salon = new Salon();
        if (request.getSalonId() != null) {
            salon.setId(UUID.fromString(request.getSalonId()));
        }
        return Seat.builder()
                .salon(salon)
                .rowNumber(request.getRowNumber())
                .seatNumber(request.getSeatNumber())
                .build();
    }
}