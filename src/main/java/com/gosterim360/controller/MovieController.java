package com.gosterim360.controller;

import com.gosterim360.common.BaseResponse;
import com.gosterim360.dto.request.MovieRequestDTO;
import com.gosterim360.dto.response.MovieResponseDTO;
import com.gosterim360.service.MovieService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "Movie", description = "API for movie management in Gosterim360")
public class MovieController {

    private final MovieService movieService;

    @Operation(
            summary = "Create a new movie",
            description = "Adds a new movie to the system",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Movie created successfully",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = BaseResponse.class))),
                    @ApiResponse(responseCode = "400", description = "Validation error",
                            content = @Content)
            }
    )
    @PostMapping
    public ResponseEntity<BaseResponse<MovieResponseDTO>> createMovie(
            @Parameter(description = "Movie request payload", required = true)
            @Valid @RequestBody MovieRequestDTO movieRequestDTO) {
        MovieResponseDTO movie = movieService.createMovie(movieRequestDTO);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(BaseResponse.success(movie, "Movie created successfully", HttpStatus.CREATED.value()));
    }


    @Operation(
            summary = "Get all movies",
            description = "Returns a list of all movies",
            responses = {
                    @ApiResponse(responseCode = "200", description = "List of movies",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = BaseResponse.class)))
            }
    )
    @GetMapping
    public ResponseEntity<BaseResponse<List<MovieResponseDTO>>> getAllMovies() {
        List<MovieResponseDTO> movies = movieService.getAllMovies();
        return ResponseEntity.ok(
                BaseResponse.success(movies, "Movies retrieved successfully", 200)
        );
    }

    @Operation(
            summary = "Get movie by ID",
            description = "Returns a movie by its UUID",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Movie found",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = BaseResponse.class))),
                    @ApiResponse(responseCode = "404", description = "Movie not found",
                            content = @Content)
            }
    )
    @GetMapping("/{id}")
    public ResponseEntity<BaseResponse<MovieResponseDTO>> getMovieById(
            @Parameter(description = "UUID of the movie to retrieve", required = true)
            @PathVariable UUID id) {
        MovieResponseDTO movie = movieService.getMovieById(id);
        return ResponseEntity.ok(
                BaseResponse.success(movie, "Movie found", 200)
        );
    }

    @Operation(
            summary = "Update movie",
            description = "Updates an existing movie by UUID",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Movie updated successfully",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = BaseResponse.class))),
                    @ApiResponse(responseCode = "400", description = "Validation error",
                            content = @Content),
                    @ApiResponse(responseCode = "404", description = "Movie not found",
                            content = @Content)
            }
    )
    @PutMapping("/{id}")
    public ResponseEntity<BaseResponse<MovieResponseDTO>> updateMovie(
            @Parameter(description = "UUID of the movie to update", required = true)
            @PathVariable UUID id,
            @Parameter(description = "Updated movie data", required = true)
            @Valid @RequestBody MovieRequestDTO movieRequestDTO) {
        MovieResponseDTO updatedMovie = movieService.updateMovie(id, movieRequestDTO);
        return ResponseEntity.ok(
                BaseResponse.success(updatedMovie, "Movie updated successfully", 200)
        );
    }

    @Operation(
            summary = "Delete movie",
            description = "Deletes a movie by UUID",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Movie deleted successfully",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = BaseResponse.class))),
                    @ApiResponse(responseCode = "404", description = "Movie not found",
                            content = @Content)
            }
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<BaseResponse<Void>> deleteMovieById(
            @Parameter(description = "UUID of the movie to delete", required = true)
            @PathVariable UUID id) {
        movieService.deleteMovieById(id);
        return ResponseEntity.ok(
                BaseResponse.success(null, "Movie deleted successfully", 200)
        );
    }
}
