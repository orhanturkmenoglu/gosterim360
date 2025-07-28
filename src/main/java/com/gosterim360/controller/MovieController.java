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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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

    @PostMapping
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
    public ResponseEntity<BaseResponse<MovieResponseDTO>> createMovie(
            @Parameter(description = "Movie request payload", required = true)
            @Valid @RequestBody MovieRequestDTO movieRequestDTO) {
        MovieResponseDTO movie = movieService.createMovie(movieRequestDTO);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(BaseResponse.success(movie, "Movie created successfully", HttpStatus.CREATED.value()));
    }

    @GetMapping
    @Operation(
            summary = "Get all movies",
            description = "Returns a list of all movies",
            responses = {
                    @ApiResponse(responseCode = "200", description = "List of movies",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = BaseResponse.class)))
            }
    )
    public ResponseEntity<BaseResponse<List<MovieResponseDTO>>> getAllMovies() {
        List<MovieResponseDTO> movies = movieService.getAllMovies();
        return ResponseEntity.ok(
                BaseResponse.success(movies, "Movies retrieved successfully", 200)
        );
    }

    @GetMapping("/{id}")
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
    public ResponseEntity<BaseResponse<MovieResponseDTO>> getMovieById(
            @Parameter(description = "UUID of the movie to retrieve", required = true)
            @PathVariable UUID id) {
        MovieResponseDTO movie = movieService.getMovieById(id);
        return ResponseEntity.ok(
                BaseResponse.success(movie, "Movie found", 200)
        );
    }

    @GetMapping("/genre/{genre}")
    @Operation(summary = "Get movies by genre", description = "Returns a list of movies filtered by genre")
    public ResponseEntity<BaseResponse<List<MovieResponseDTO>>> getMoviesByGenre(
            @Parameter(description = "Genre to filter movies", required = true)
            @PathVariable String genre) {
        List<MovieResponseDTO> movies = movieService.getMoviesByGenre(genre);
        return ResponseEntity.ok(
                BaseResponse.success(movies, "Movies filtered by genre retrieved successfully", 200)
        );
    }

    @GetMapping("/rating-above/{rating}")
    @Operation(summary = "Get movies with rating above given value", description = "Returns movies with rating greater than the specified value")
    public ResponseEntity<BaseResponse<List<MovieResponseDTO>>> getMoviesByRatingGreaterThan(
            @Parameter(description = "Minimum rating", required = true)
            @PathVariable Double rating) {
        List<MovieResponseDTO> movies = movieService.getMoviesByRatingGreaterThan(rating);
        return ResponseEntity.ok(
                BaseResponse.success(movies, "Movies with high rating retrieved successfully", 200)
        );
    }

    @GetMapping("/sorted-by-rating")
    @Operation(summary = "Get all movies sorted by rating (desc)", description = "Returns all movies sorted by rating descending")
    public ResponseEntity<BaseResponse<List<MovieResponseDTO>>> getAllMoviesSortedByRating() {
        List<MovieResponseDTO> movies = movieService.getAllMoviesSortedByRating();
        return ResponseEntity.ok(
                BaseResponse.success(movies, "Movies sorted by rating retrieved successfully", 200)
        );
    }

    @GetMapping("/sorted-by-review-count")
    @Operation(summary = "Get all movies sorted by review count (desc)", description = "Returns all movies sorted by review count descending")
    public ResponseEntity<BaseResponse<List<MovieResponseDTO>>> getAllMoviesSortedByReviewCount() {
        List<MovieResponseDTO> movies = movieService.getAllMoviesSortedByReviewCount();
        return ResponseEntity.ok(
                BaseResponse.success(movies, "Movies sorted by review count retrieved successfully", 200)
        );
    }

    @GetMapping("/sorted-by-created-date")
    @Operation(summary = "Get all movies sorted by creation date (desc)", description = "Returns all movies sorted by creation date descending")
    public ResponseEntity<BaseResponse<List<MovieResponseDTO>>> getAllMoviesSortedByCreatedDate() {
        List<MovieResponseDTO> movies = movieService.getAllMoviesSortedByCreatedDate();
        return ResponseEntity.ok(
                BaseResponse.success(movies, "Movies sorted by creation date retrieved successfully", 200)
        );
    }

    @GetMapping("/paged")
    @Operation(summary = "Get movies with pagination", description = "Returns movies paginated")
    public ResponseEntity<BaseResponse<Page<MovieResponseDTO>>> getMoviesWithPagination(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "createdAt") String sortBy,
            @RequestParam(defaultValue = "desc") String direction) {

        Pageable pageable = direction.equalsIgnoreCase("desc") ?
                PageRequest.of(page, size, Sort.by(sortBy).descending()) :
                PageRequest.of(page, size, Sort.by(sortBy).ascending());

        Page<MovieResponseDTO> moviePage = movieService.getMovies(pageable);
        return ResponseEntity.ok(
                BaseResponse.success(moviePage, "Paginated movies retrieved successfully", 200)
        );
    }

    @GetMapping("/search")
    @Operation(summary = "Search movies by keyword", description = "Search movies by name, genre, or description")
    public ResponseEntity<BaseResponse<List<MovieResponseDTO>>> searchMovies(
            @RequestParam("keyword") String keyword) {
        List<MovieResponseDTO> movies = movieService.searchMovies(keyword);
        return ResponseEntity.ok(
                BaseResponse.success(movies, "Movies matching search keyword retrieved successfully", 200)
        );
    }

    @PutMapping("/{id}")
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

    @DeleteMapping("/{id}")
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
    public ResponseEntity<BaseResponse<Void>> deleteMovieById(
            @Parameter(description = "UUID of the movie to delete", required = true)
            @PathVariable UUID id) {
        movieService.deleteMovieById(id);
        return ResponseEntity.ok(
                BaseResponse.success(null, "Movie deleted successfully", 200)
        );
    }
}
