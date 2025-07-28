package com.gosterim360.mapper;

import com.gosterim360.common.BaseMapper;
import com.gosterim360.dto.request.SessionRequestDTO;
import com.gosterim360.dto.response.SessionResponseDTO;
import com.gosterim360.dto.response.SessionTimeResponseDTO;
import com.gosterim360.model.Salon;
import com.gosterim360.model.Session;
import com.gosterim360.model.SessionTime;
import com.gosterim360.repository.SalonRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class SessionMapper extends BaseMapper<Session, SessionResponseDTO, SessionRequestDTO> {

    private final SalonRepository salonRepository;

    @Override
    public SessionResponseDTO toDTO(Session entity) {

        if (entity == null) {
            return null;
        }

        List<SessionTimeResponseDTO> timeDTOs = entity.getTimes().stream()
                .map(time -> SessionTimeResponseDTO.builder()
                        .id(time.getId())
                        .time(time.getTime())
                        .price(time.getPrice())
                        .createdAt(time.getCreatedAt())
                        .updatedAt(time.getUpdatedAt())
                        .build())
                .toList();

        return SessionResponseDTO.builder()
                .id(entity.getId())
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
                .build();

        if (request.getSalonId() != null) {
            Salon salon = salonRepository.findById(request.getSalonId())
                    .orElseThrow(() -> new RuntimeException("Salon not found with id: " + request.getSalonId()));
            session.setSalon(salon);
        }

        if (request.getTimes() != null) {
            List<SessionTime> times = request.getTimes().stream()
                    .map(timeDTO -> {
                        SessionTime time = new SessionTime();
                        time.setPrice(timeDTO.getPrice());
                        time.setTime(timeDTO.getTime());
                        time.setSession(session); // ilişkiyi burada kur
                        return time;
                    })
                    .toList();

            session.setTimes(times);
        }

        return session;
    }


    public Session toEntity(SessionResponseDTO responseDTO) {
        if (responseDTO == null) {
            return null;
        }

        List<SessionTime> sessionTimes = responseDTO.getTimes().stream()
                .map(timeDto ->
                        SessionTime.builder()
                                .price(timeDto.getPrice())
                                .time(timeDto.getTime())
                                .build())
                .toList();

        return Session.builder()
                .times(sessionTimes)
                .date(responseDTO.getDate())
                .build();
    }
}
