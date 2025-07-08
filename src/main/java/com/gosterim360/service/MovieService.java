package com.gosterim360.service;

import com.gosterim360.dto.request.MovieRequestDTO;
import com.gosterim360.dto.response.MovieResponseDTO;

import java.util.List;
import java.util.UUID;

public interface MovieService {

    MovieResponseDTO createMovie(MovieRequestDTO movieRequestDTO);

    List<MovieResponseDTO> getAllMovies();

    MovieResponseDTO getMovieById(UUID id);

    MovieResponseDTO  updateMovie(UUID id, MovieRequestDTO movieRequestDTO);

    void deleteMovieById(UUID id);
}
