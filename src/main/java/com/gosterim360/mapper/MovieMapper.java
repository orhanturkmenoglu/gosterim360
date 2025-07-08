package com.gosterim360.mapper;

import com.gosterim360.dto.request.MovieRequestDTO;
import com.gosterim360.dto.response.MovieResponseDTO;
import com.gosterim360.model.Movie;

import java.util.List;

public class MovieMapper {

    public static Movie toEntity(MovieRequestDTO movieRequestDTO) {
      return Movie.builder()
              .name(movieRequestDTO.getName())
              .description(movieRequestDTO.getDescription())
              .genre(movieRequestDTO.getGenre())
              .duration(movieRequestDTO.getDuration())
              .rating(movieRequestDTO.getRating())
              .reviewCount(movieRequestDTO.getReviewCount())
              .posterUrl(movieRequestDTO.getPosterUrl())
              .build();
    }

    public static MovieResponseDTO toDTO(Movie movie) {
        return MovieResponseDTO.builder()
                .id(movie.getId())
                .name(movie.getName())
                .description(movie.getDescription())
                .genre(movie.getGenre())
                .duration(movie.getDuration())
                .rating(movie.getRating())
                .reviewCount(movie.getReviewCount())
                .posterUrl(movie.getPosterUrl())
                .createdAt(movie.getCreatedAt())
                .updatedAt(movie.getUpdatedAt())
                .build();
    }


    public static List<MovieResponseDTO> toDTOList(List<Movie> movies) {
        return movies.stream()
                .map(MovieMapper::toDTO).toList();
    }
}
