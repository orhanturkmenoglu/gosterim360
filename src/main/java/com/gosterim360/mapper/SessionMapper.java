package com.gosterim360.mapper;

import com.gosterim360.common.BaseMapper;
import com.gosterim360.dto.request.SessionRequestDTO;
import com.gosterim360.dto.response.SessionResponseDTO;
import com.gosterim360.dto.response.SessionTimeResponseDTO;
import com.gosterim360.model.Session;
import com.gosterim360.model.SessionTime;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class SessionMapper extends BaseMapper<Session, SessionResponseDTO, SessionRequestDTO> {

    @Override
    public SessionResponseDTO toDTO(Session entity) {

        if (entity == null) {
            return null;
        }


        List<SessionTimeResponseDTO> timeDTOs = entity.getTimes().stream()
                .map(time -> SessionTimeResponseDTO.builder()
                        .id(time.getId())
                        .time(time.getTime())
                        .createdAt(time.getCreatedAt())
                        .updatedAt(time.getUpdatedAt())
                        .build())
                .toList();

        return SessionResponseDTO.builder()
                .id(entity.getId())
                .price(entity.getPrice())
                .date(entity.getDate())
                .times(timeDTOs)
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .build();
    }

    @Override
    public Session toEntity(SessionRequestDTO request) {
        if (request == null) {
            return null;
        }

        Session session = Session.builder()
                .date(request.getDate())
                .price(request.getPrice())
                .build();

        if (request.getTimes() != null) {
            List<SessionTime> times = request.getTimes().stream()
                    .map(timeDTO -> {
                        SessionTime time = new SessionTime();
                        time.setTime(timeDTO.getTime());
                        time.setSession(session); // ilişkiyi burada kur
                        return time;
                    })
                    .toList();

            session.setTimes(times);
        }

        return session;
    }
}
