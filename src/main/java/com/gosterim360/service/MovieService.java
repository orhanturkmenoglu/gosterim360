package com.gosterim360.service;

import com.gosterim360.dto.request.MovieRequestDTO;
import com.gosterim360.dto.response.MovieResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;

public interface MovieService {

    MovieResponseDTO createMovie(MovieRequestDTO movieRequestDTO);

    List<MovieResponseDTO> searchMovies(String keyword);

    List<MovieResponseDTO> getMoviesByGenre(String genre);

    List<MovieResponseDTO> getMoviesByRatingGreaterThan(Double rating);

    List<MovieResponseDTO> getAllMoviesSortedByRating();

    List<MovieResponseDTO> getAllMoviesSortedByReviewCount();

    List<MovieResponseDTO> getAllMoviesSortedByCreatedDate();

    Page<MovieResponseDTO> getMovies(Pageable pageable);

    List<MovieResponseDTO> getAllMovies();

    MovieResponseDTO getMovieById(UUID id);

    MovieResponseDTO  updateMovie(UUID id, MovieRequestDTO movieRequestDTO);

    void deleteMovieById(UUID id);
}
