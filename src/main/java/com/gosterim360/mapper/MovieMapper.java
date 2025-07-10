package com.gosterim360.mapper;

import com.gosterim360.common.BaseMapper;
import com.gosterim360.dto.request.MovieRequestDTO;
import com.gosterim360.dto.response.MovieResponseDTO;
import com.gosterim360.model.Movie;
import org.springframework.stereotype.Component;

@Component
public class MovieMapper extends BaseMapper<Movie,MovieResponseDTO,MovieRequestDTO> {

    @Override
    public MovieResponseDTO toDTO(Movie entity) {

        if (entity == null) {
            return null;
        }

        return MovieResponseDTO.builder()
                .id(entity.getId())
                .name(entity.getName())
                .description(entity.getDescription())
                .genre(entity.getGenre())
                .duration(entity.getDuration())
                .rating(entity.getRating())
                .reviewCount(entity.getReviewCount())
                .posterUrl(entity.getPosterUrl())
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .build();
    }

    @Override
    public Movie toEntity(MovieRequestDTO request) {

        if (request == null) {
            return null;
        }


        return Movie.builder()
                .name(request.getName())
                .description(request.getDescription())
                .genre(request.getGenre())
                .duration(request.getDuration())
                .rating(request.getRating())
                .reviewCount(request.getReviewCount())
                .posterUrl(request.getPosterUrl())
                .build();
    }
}
