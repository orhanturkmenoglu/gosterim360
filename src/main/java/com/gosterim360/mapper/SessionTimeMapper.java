package com.gosterim360.mapper;

import com.gosterim360.common.BaseMapper;
import com.gosterim360.dto.request.SessionTimeRequestDTO;
import com.gosterim360.dto.response.SessionTimeResponseDTO;
import com.gosterim360.model.SessionTime;
import org.springframework.stereotype.Component;

@Component
public class SessionTimeMapper extends BaseMapper<SessionTime, SessionTimeResponseDTO, SessionTimeRequestDTO> {

    @Override
    public SessionTimeResponseDTO toDTO(SessionTime entity) {

        if (entity == null) {
            return null;
        }

        return SessionTimeResponseDTO.builder()
                .id(entity.getId())
                .time(entity.getTime())
                .price(entity.getPrice())
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .build();
    }

    @Override
    public SessionTime toEntity(SessionTimeRequestDTO request) {

        if (request == null) {
            return null;
        }

        return SessionTime.builder()
                .price(request.getPrice())
                .time(request.getTime())
                .build();
    }
}
