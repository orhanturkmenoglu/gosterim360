package com.gosterim360.mapper;

import com.gosterim360.common.BaseMapper;
import com.gosterim360.dto.request.SalonRequestDTO;
import com.gosterim360.dto.response.SalonResponseDTO;
import com.gosterim360.model.Salon;
import org.springframework.stereotype.Component;

@Component
public class SalonMapper extends BaseMapper<Salon, SalonResponseDTO, SalonRequestDTO> {

    @Override
    public SalonResponseDTO toDTO(Salon entity) {
        return SalonResponseDTO.builder()
                .id(entity.getId())
                .name(entity.getName())
                .location(entity.getLocation())
                .seatCapacity(entity.getSeatCapacity())
                .createdAt(entity.getCreatedAt() != null ? entity.getCreatedAt().atZone(java.time.ZoneId.systemDefault()).toInstant() : null)
                .updatedAt(entity.getUpdatedAt() != null ? entity.getUpdatedAt().atZone(java.time.ZoneId.systemDefault()).toInstant() : null)
                .build();
    }

    @Override
    public Salon toEntity(SalonRequestDTO dto) {
        return Salon.builder()
                .name(dto.getName())
                .location(dto.getLocation())
                .seatCapacity(dto.getSeatCapacity())
                .build();
    }
}