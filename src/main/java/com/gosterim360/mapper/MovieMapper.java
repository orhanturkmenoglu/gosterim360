package com.gosterim360.mapper;

import com.gosterim360.common.BaseMapper;
import com.gosterim360.dto.request.MovieRequestDTO;
import com.gosterim360.dto.request.SessionRequestDTO;
import com.gosterim360.dto.response.MovieResponseDTO;
import com.gosterim360.dto.response.SessionResponseDTO;
import com.gosterim360.dto.response.SessionTimeResponseDTO;
import com.gosterim360.model.Movie;
import com.gosterim360.model.Session;
import com.gosterim360.model.SessionTime;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class MovieMapper extends BaseMapper<Movie, MovieResponseDTO, MovieRequestDTO> {

    private final SessionMapper sessionMapper;
    private final SessionTimeMapper sessionTimeMapper;

    @Override
    public MovieResponseDTO toDTO(Movie entity) {
        if (entity == null) {
            return null;
        }

        List<SessionResponseDTO> sessionDTOs = entity.getSessions()
                .stream()
                .map(session -> {
                    List<SessionTimeResponseDTO> timeDTOs = session.getTimes()
                            .stream()
                            .map(sessionTimeMapper::toDTO)
                            .toList();

                    return SessionResponseDTO.builder()
                            .id(session.getId())
                            .date(session.getDate())
                            .times(timeDTOs)
                            .createdAt(session.getCreatedAt())
                            .updatedAt(session.getUpdatedAt())
                            .build();
                })
                .toList();

        return MovieResponseDTO.builder()
                .id(entity.getId())
                .name(entity.getName())
                .description(entity.getDescription())
                .genre(entity.getGenre())
                .duration(entity.getDuration())
                .rating(entity.getRating())
                .reviewCount(entity.getReviewCount())
                .posterUrl(entity.getPosterUrl())
                .sessionResponseDTOList(sessionDTOs)
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .build();
    }

    @Override
    public Movie toEntity(MovieRequestDTO request) {
        if (request == null) {
            return null;
        }

        Movie movie = Movie.builder()
                .name(request.getName())
                .description(request.getDescription())
                .genre(request.getGenre())
                .duration(request.getDuration())
                .rating(request.getRating())
                .reviewCount(request.getReviewCount())
                .posterUrl(request.getPosterUrl())
                .build();

        if (request.getSessions() != null) {
            List<Session> sessions = request.getSessions().stream()
                    .map(sessionRequestDTO -> mapSessionRequestToEntity(sessionRequestDTO, movie))
                    .toList();

            movie.setSessions(sessions);
        }

        return movie;
    }

    public Movie toEntity(MovieResponseDTO dto) {
        if (dto == null) {
            return null;
        }

        Movie movie = Movie.builder()
                .name(dto.getName())
                .description(dto.getDescription())
                .genre(dto.getGenre())
                .duration(dto.getDuration())
                .rating(dto.getRating())
                .reviewCount(dto.getReviewCount())
                .posterUrl(dto.getPosterUrl())
                .build();

        if (dto.getSessionResponseDTOList() != null) {
            List<Session> sessions = dto.getSessionResponseDTOList().stream()
                    .map(sessionDTO -> {
                        Session session = sessionMapper.toEntity(sessionDTO);
                        session.setMovie(movie); // movie_id boş kalmasın
                        return session;
                    })
                    .toList();

            movie.setSessions(sessions);
        }

        return movie;
    }

    // Yardımcı method – request DTO'dan Session entity üretir ve movie ilişkilendirir
    private Session mapSessionRequestToEntity(SessionRequestDTO dto, Movie movie) {
        Session session = Session.builder()
                .date(dto.getDate())
                .movie(movie) // kritik: movie_id burada bağlanıyor
                .build();

        if (dto.getTimes() != null) {
            List<SessionTime> times = dto.getTimes().stream()
                    .map(timeDTO -> SessionTime.builder()
                            .time(timeDTO.getTime())
                            .session(session) // kritik: session_id burada bağlanıyor
                            .build())
                    .toList();

            session.setTimes(times);
        }

        return session;
    }
}
