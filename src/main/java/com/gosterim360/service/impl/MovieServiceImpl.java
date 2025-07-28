package com.gosterim360.service.impl;

import com.gosterim360.common.MessageUtil;
import com.gosterim360.dto.request.MovieRequestDTO;
import com.gosterim360.dto.response.MovieResponseDTO;
import com.gosterim360.exception.MovieNotFoundException;
import com.gosterim360.mapper.MovieMapper;
import com.gosterim360.model.Movie;
import com.gosterim360.repository.MovieRepository;
import com.gosterim360.service.MovieService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class MovieServiceImpl implements MovieService {

    private final MovieRepository movieRepository;
    private final MessageUtil messageUtil;
    private final MovieMapper movieMapper;

    @Transactional
    @Override
    public MovieResponseDTO createMovie(MovieRequestDTO movieRequestDTO) {
        log.info("MovieServiceImpl:: createMovie started");

        Optional<Movie> optionalMovie = movieRepository.findMovieByName(movieRequestDTO.getName());

        if (optionalMovie.isPresent()) {
            log.error("MovieServiceImpl:: createMovie failed ");
            throw new MovieNotFoundException(messageUtil.getMessage("movie.already.exists", movieRequestDTO.getName()));
        }

        Movie movie = movieMapper.toEntity(movieRequestDTO);
        log.info("MovieServiceImpl:: movie toEntity   {}", movie);

        Movie savedMovie = movieRepository.save(movie);
        log.info("MovieServiceImpl:: saved movie  {}", savedMovie);

        log.info("MovieServiceImpl:: createMovie finished");
        return movieMapper.toDTO(savedMovie);
    }

    @Override
    public List<MovieResponseDTO> searchMovies(String keyword) {
        log.info("MovieServiceImpl:: searchMovies started");

        List<Movie> movies = movieRepository.
                findByNameContainingIgnoreCaseOrDescriptionContainingIgnoreCaseOrGenreContainingIgnoreCase(keyword, keyword, keyword);

        log.info("MovieServiceImpl::movies {}",movies);

        log.info("MovieServiceImpl:: searchMovies finished");

        return movies.stream()
                .map(movieMapper::toDTO)
                .toList();

    }

    @Override
    public List<MovieResponseDTO> getMoviesByGenre(String genre) {
        log.info("MovieServiceImpl:: getMoviesByGenre started");
        List<Movie> movies = movieRepository.findMovieByGenreIgnoreCase(genre);


        log.info("MovieServiceImpl:: getMoviesByGenre finished");
        return movies.stream()
                .map(movieMapper::toDTO)
                .toList();
    }

    @Override
    public List<MovieResponseDTO> getMoviesByRatingGreaterThan(Double rating) {
        log.info("MovieServiceImpl:: getMoviesByRatingGreaterThan started");
        List<Movie> movies = movieRepository.findByRatingGreaterThan(rating);

        log.info("MovieServiceImpl:: getMoviesByRatingGreaterThan finished");
        return movies.stream()
                .map(movieMapper::toDTO)
                .toList();
    }

    @Override
    public List<MovieResponseDTO> getAllMoviesSortedByRating() {
        log.info("MovieServiceImpl:: getAllMoviesSortedByRating started");
        List<Movie> movies = movieRepository.findAllByOrderByRatingDesc();

        log.info("MovieServiceImpl:: getAllMoviesSortedByRating finished");
        return movies.stream()
                .map(movieMapper::toDTO)
                .toList();
    }

    @Override
    public List<MovieResponseDTO> getAllMoviesSortedByReviewCount() {
        List<Movie> movies = movieRepository.findAllByOrderByReviewCountDesc();
        return movies.stream()
                .map(movieMapper::toDTO)
                .toList();
    }

    @Override
    public List<MovieResponseDTO> getAllMoviesSortedByCreatedDate() {
        List<Movie> movies = movieRepository.findAllByOrderByCreatedAtDesc();
        return movies.stream()
                .map(movieMapper::toDTO)
                .toList();
    }

    @Override
    public Page<MovieResponseDTO> getMovies(Pageable pageable) {
        return movieRepository.findAll(pageable)
                .map(movieMapper::toDTO);
    }

    @Override
    public List<MovieResponseDTO> getAllMovies() {
        log.info("MovieServiceImpl:: getAllMovies started");

        List<Movie> movies = movieRepository.findAll();
        log.info("MovieServiceImpl:: movies found  {}", movies);


        log.info("MovieServiceImpl:: getAllMovies finished");
        return movieMapper.toDTOList(movies);
    }

    @Override
    public MovieResponseDTO getMovieById(UUID id) {
        log.info("MovieServiceImpl:: getMovieById started");

        Movie movie = movieRepository.findById(id)
                .orElseThrow(() -> new MovieNotFoundException(messageUtil.getMessage("movie.notfound", id)));
        log.info("MovieServiceImpl:: movie found  {}", movie);


        log.info("MovieServiceImpl:: getMovieById finished");
        return movieMapper.toDTO(movie);
    }

    @Override
    public MovieResponseDTO updateMovie(UUID id, MovieRequestDTO movieRequestDTO) {
        log.info("MovieServiceImpl:: updateMovie started");

        MovieResponseDTO movieResponseDTO = getMovieById(id);

        movieResponseDTO.setName(movieRequestDTO.getName());
        movieResponseDTO.setDescription(movieRequestDTO.getDescription());
        movieResponseDTO.setDuration(movieRequestDTO.getDuration());
        movieResponseDTO.setGenre(movieRequestDTO.getGenre());
        movieResponseDTO.setPosterUrl(movieRequestDTO.getPosterUrl());
        movieResponseDTO.setRating(movieRequestDTO.getRating());
        movieResponseDTO.setReviewCount(movieRequestDTO.getReviewCount());


        Movie movie = movieMapper.toEntity(movieResponseDTO);
        log.info("MovieServiceImpl:: updated movie  {}", movie);

        Movie updatedMovie = movieRepository.save(movie);

        return movieMapper.toDTO(updatedMovie);
    }

    @Override
    public void deleteMovieById(UUID id) {
        log.info("MovieServiceImpl:: deleteMovieById started");

        MovieResponseDTO movie = getMovieById(id);
        log.info("MovieServiceImpl::deleteMovieById movie found  {}", movie);

        movieRepository.deleteById(id);
        log.info("MovieServiceImpl:: deleteMovieById finished");
    }
}

