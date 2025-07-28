package com.gosterim360.repository;

import com.gosterim360.model.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface MovieRepository extends JpaRepository<Movie, UUID> {

    Optional<Movie> findMovieByName(String movieName);

    // Bu method, girilen keyword’ü hem film adı, hem açıklama hem de tür içinde küçük-büyük harf duyarsız şekilde arar.
    List<Movie> findByNameContainingIgnoreCaseOrDescriptionContainingIgnoreCaseOrGenreContainingIgnoreCase(
            String name, String description, String genre
    );

    List<Movie> findMovieByGenreIgnoreCase(String genre);

    List<Movie> findAllByOrderByRatingDesc();

    List<Movie> findAllByOrderByReviewCountDesc();

    List<Movie> findAllByOrderByCreatedAtDesc();

    List<Movie> findByRatingGreaterThan(Double ratingIsGreaterThan);
}
