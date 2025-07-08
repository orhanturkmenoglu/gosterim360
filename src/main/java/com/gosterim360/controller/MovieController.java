package com.gosterim360.controller;

import com.gosterim360.common.BaseResponse;
import com.gosterim360.dto.request.MovieRequestDTO;
import com.gosterim360.dto.response.MovieResponseDTO;
import com.gosterim360.service.MovieService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/movies")
@RequiredArgsConstructor
public class MovieController {

    private final MovieService movieService;

    @PostMapping
    public ResponseEntity<BaseResponse<MovieResponseDTO>> createMovie(@Valid @RequestBody MovieRequestDTO movieRequestDTO) {
        MovieResponseDTO movie = movieService.createMovie(movieRequestDTO);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(BaseResponse.success(movie,"Movie created successfully",HttpStatus.CREATED.value()));
    }

    @GetMapping
    public ResponseEntity<BaseResponse<List<MovieResponseDTO>>> getAllMovies() {
        List<MovieResponseDTO> movies = movieService.getAllMovies();
        return ResponseEntity.ok(
                BaseResponse.success(movies, "Movies retrieved successfully", 200)
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<BaseResponse<MovieResponseDTO>> getMovieById(@PathVariable UUID id) {
        MovieResponseDTO movie = movieService.getMovieById(id);
        return ResponseEntity.ok(
                BaseResponse.success(movie, "Movie found", 200)
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<BaseResponse<MovieResponseDTO>> updateMovie(@PathVariable UUID id,
                                                                      @Valid @RequestBody MovieRequestDTO movieRequestDTO) {
        MovieResponseDTO updatedMovie = movieService.updateMovie(id, movieRequestDTO);
        return ResponseEntity.ok(
                BaseResponse.success(updatedMovie, "Movie updated successfully", 200)
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<BaseResponse<Void>> deleteMovieById(@PathVariable UUID id) {
        movieService.deleteMovieById(id);
        return ResponseEntity.ok(
                BaseResponse.success(null, "Movie deleted successfully", 200)
        );
    }
}
