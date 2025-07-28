package com.gosterim360.mapper;

import com.gosterim360.common.BaseMapper;
import com.gosterim360.dto.request.SalonRequestDTO;
import com.gosterim360.dto.response.SalonResponseDTO;
import com.gosterim360.dto.response.SeatResponseDTO;
import com.gosterim360.model.Salon;
import com.gosterim360.model.Seat;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class SalonMapper extends BaseMapper<Salon, SalonResponseDTO, SalonRequestDTO> {

    private final SeatMapper seatMapper;

    @Override
    public SalonResponseDTO toDTO(Salon entity) {

        List<SeatResponseDTO> seats = null;

        if(entity.getSeats()!=null){
            seats=entity.getSeats().stream()
                    .map(seatMapper::toDTO)
                    .toList();
        }

        return SalonResponseDTO.builder()
                .id(entity.getId())
                .name(entity.getName())
                .location(entity.getLocation())
                .seatCapacity(entity.getSeatCapacity())
                .createdAt(entity.getCreatedAt() != null ? entity.getCreatedAt().atZone(java.time.ZoneId.systemDefault()).toInstant() : null)
                .updatedAt(entity.getUpdatedAt() != null ? entity.getUpdatedAt().atZone(java.time.ZoneId.systemDefault()).toInstant() : null)
                .seats(seats)
                .build();
    }

    @Override
    public Salon toEntity(SalonRequestDTO dto) {

        List<Seat> seats = null;
        if (dto.getSeatRequestDTOList() != null) {
            seats = dto.getSeatRequestDTOList().stream()
                    .map(seatReqDTO ->
                            seatMapper.toEntity(seatReqDTO))
                    .toList();
        }

        Salon salon = Salon.builder()
                .name(dto.getName())
                .location(dto.getLocation())
                .seatCapacity(dto.getSeatCapacity())
                .build();

        if (seats != null) {
            seats.forEach(seat -> seat.setSalon(salon));
            salon.setSeats(seats);
        }
        return salon;
    }
}