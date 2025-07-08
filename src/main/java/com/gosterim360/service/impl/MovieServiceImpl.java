package com.gosterim360.service.impl;

import com.gosterim360.dto.request.MovieRequestDTO;
import com.gosterim360.dto.response.MovieResponseDTO;
import com.gosterim360.exception.MovieNotFoundException;
import com.gosterim360.mapper.MovieMapper;
import com.gosterim360.model.Movie;
import com.gosterim360.repository.MovieRepository;
import com.gosterim360.service.MovieService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class MovieServiceImpl implements MovieService {

    private final MovieRepository movieRepository;

    @Override
    public MovieResponseDTO createMovie(MovieRequestDTO movieRequestDTO) {
        log.info("MovieServiceImpl:: createMovie started");

        Movie movie= MovieMapper.toEntity(movieRequestDTO);
        log.info("MovieServiceImpl:: movie toEntity   {}", movie);

        Movie savedMovie = movieRepository.save(movie);
        log.info("MovieServiceImpl:: saved movie  {}", savedMovie);

        log.info("MovieServiceImpl:: createMovie finished");
        return MovieMapper.toDTO(savedMovie);
    }

    @Override
    public MovieResponseDTO getMovieById(UUID id) {
       log.info("MovieServiceImpl:: getMovieById started");

        Movie movie = movieRepository.findById(id)
                .orElseThrow(() -> new MovieNotFoundException("Movie not found"));
        log.info("MovieServiceImpl:: movie found  {}", movie);


        log.info("MovieServiceImpl:: getMovieById finished");
        return MovieMapper.toDTO(movie);
    }

    @Override
    public MovieResponseDTO updateMovie(UUID id, MovieRequestDTO movieRequestDTO) {
        return null;
    }

    @Override
    public void deleteMovieById(UUID id) {
        log.info("MovieServiceImpl:: deleteMovieById started");

        MovieResponseDTO movie = getMovieById(id);
        log.info("MovieServiceImpl:: movie found  {}", movie);

        movieRepository.deleteById(id);
        log.info("MovieServiceImpl:: deleteMovieById finished");
    }
}

