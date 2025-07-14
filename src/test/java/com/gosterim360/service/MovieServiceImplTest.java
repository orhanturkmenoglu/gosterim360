package com.gosterim360.service;


import com.gosterim360.dto.request.MovieRequestDTO;
import com.gosterim360.dto.request.SessionRequestDTO;
import com.gosterim360.dto.request.SessionTimeRequestDTO;
import com.gosterim360.dto.response.MovieResponseDTO;
import com.gosterim360.exception.MovieNotFoundException;
import com.gosterim360.mapper.MovieMapper;
import com.gosterim360.model.Movie;
import com.gosterim360.repository.MovieRepository;
import com.gosterim360.service.impl.MovieServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class MovieServiceImplTest {

    @InjectMocks
    private MovieServiceImpl movieService;

    @Mock
    private MovieRepository movieRepository;

    @Mock
    private MovieMapper movieMapper;

    private MovieRequestDTO movieRequestDTO;
    private MovieResponseDTO movieResponseDTO;
    private SessionRequestDTO sessionRequestDTO;
    private SessionTimeRequestDTO sessionTimeRequestDTO;
    private Movie movie;

    @BeforeEach
    public void setUp() {
        sessionTimeRequestDTO = SessionTimeRequestDTO.builder()
                .time(LocalDateTime.of(2025, 7, 15, 18, 30))
                .build();

        List<SessionTimeRequestDTO> sessionTimeList = List.of(sessionTimeRequestDTO);

        sessionRequestDTO = SessionRequestDTO.builder()
                .date(LocalDate.of(2025, 7, 15))
                .price(BigDecimal.valueOf(125.00))
                .times(sessionTimeList)
                .build();

        List<SessionRequestDTO> sessionRequestDTOList = List.of(sessionRequestDTO);

        movieRequestDTO = MovieRequestDTO.builder()
                .name("Inception")
                .description("A mind-bending thriller")
                .genre("Sci-Fi")
                .duration(148.0)
                .rating(8.8)
                .reviewCount(1000)
                .posterUrl("poster.jpg")
                .sessions(sessionRequestDTOList)
                .build();

        movie = Movie.builder()
                .name("Inception")
                .description("A mind-bending thriller")
                .genre("Sci-Fi")
                .duration(148.0)
                .rating(8.8)
                .reviewCount(1000)
                .posterUrl("poster.jpg")
                .build();

        movieResponseDTO = MovieResponseDTO.builder()
                .id(UUID.randomUUID())
                .name("Inception")
                .description("A mind-bending thriller")
                .genre("Sci-Fi")
                .duration(148.0)
                .rating(8.8)
                .reviewCount(1000)
                .posterUrl("poster.jpg")
                .build();
    }

    @Test
    public void createMovie_ShouldReturnMovieResponseDTO_WhenValidRequest() {
        when(movieRepository.findMovieByName(movieRequestDTO.getName())).thenReturn(Optional.empty());
        when(movieMapper.toEntity(movieRequestDTO)).thenReturn(movie);
        when(movieRepository.save(movie)).thenReturn(movie);
        when(movieMapper.toDTO(movie)).thenReturn(movieResponseDTO);

        MovieResponseDTO result = movieService.createMovie(movieRequestDTO);

        assertNotNull(result);
        assertEquals("Inception", result.getName());
        verify(movieRepository).save(movie);
    }

    @Test
    public void getAllMovies_ReturnsListOfMovieResponseDto_WhenMoviesExists(){

        when(movieRepository.findAll()).thenReturn(List.of(movie));
        when(movieMapper.toDTOList(List.of(movie))).thenReturn(List.of(movieResponseDTO));

        List<MovieResponseDTO> allMovies = movieService.getAllMovies();

        assertNotNull(allMovies);
        verify(movieRepository,times(1)).findAll();
    }

    @Test
    public void getMovieById_ReturnsMovieResponseDto_WhenMovieDoesNotExist(){

        UUID movieId = UUID.randomUUID();
        when(movieRepository.findById(movieId)).thenReturn(Optional.empty());


       assertThrows(NullPointerException.class,
               () -> movieService.getMovieById(movieId));

       verify(movieRepository, times(1)).findById(movieId);

    }

    @Test
    public void updateMovie_ShouldUpdateMovie_WhenValidRequest() {
        // Arrange
        UUID movieId = UUID.randomUUID();

        Movie movieFromDb = Movie.builder()
                .name("Old Name")
                .description("Old Desc")
                .duration(100.0)
                .genre("Old Genre")
                .posterUrl("old.jpg")
                .rating(7.0)
                .reviewCount(100)
                .build();

        MovieResponseDTO movieResponseFromDb = MovieResponseDTO.builder()
                .id(movieId)
                .name("Old Name")
                .description("Old Desc")
                .duration(100.0)
                .genre("Old Genre")
                .posterUrl("old.jpg")
                .rating(7.0)
                .reviewCount(100)
                .build();

        MovieRequestDTO updatedRequest = MovieRequestDTO.builder()
                .name("Updated Name")
                .description("Updated Desc")
                .duration(120.0)
                .genre("Updated Genre")
                .posterUrl("updated.jpg")
                .rating(8.5)
                .reviewCount(500)
                .sessions(List.of())
                .build();

        Movie updatedMovieEntity = Movie.builder()
                .name("Updated Name")
                .description("Updated Desc")
                .duration(120.0)
                .genre("Updated Genre")
                .posterUrl("updated.jpg")
                .rating(8.5)
                .reviewCount(500)
                .build();

        MovieResponseDTO updatedMovieResponse = MovieResponseDTO.builder()
                .id(movieId)
                .name("Updated Name")
                .description("Updated Desc")
                .duration(120.0)
                .genre("Updated Genre")
                .posterUrl("updated.jpg")
                .rating(8.5)
                .reviewCount(500)
                .build();

        // Stub'lar
        when(movieRepository.findById(movieId)).thenReturn(Optional.of(movieFromDb));
        when(movieMapper.toDTO(movieFromDb)).thenReturn(movieResponseFromDb);
        when(movieMapper.toEntity(any(MovieResponseDTO.class))).thenReturn(updatedMovieEntity);
        when(movieRepository.save(updatedMovieEntity)).thenReturn(updatedMovieEntity);
        when(movieMapper.toDTO(updatedMovieEntity)).thenReturn(updatedMovieResponse);

        // Act
        MovieResponseDTO result = movieService.updateMovie(movieId, updatedRequest);

        // Assert
        assertNotNull(result);
        assertEquals("Updated Name", result.getName());
        assertEquals("Updated Desc", result.getDescription());
        assertEquals(8.5, result.getRating());
        verify(movieRepository).save(updatedMovieEntity);
    }

    @Test
    public void deleteMovieById_ShouldDeleteMovie_WhenMovieExists() {
        UUID movieId = UUID.randomUUID();
        when(movieRepository.findById(movieId)).thenReturn(Optional.of(movie));

        movieService.deleteMovieById(movieId);

        verify(movieRepository, times(1)).deleteById(movieId);
    }

}
