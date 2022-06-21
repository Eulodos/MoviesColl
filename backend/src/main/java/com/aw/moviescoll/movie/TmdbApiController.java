package com.aw.moviescoll.movie;

import com.aw.moviescoll.movie.domain.TmdbApiClient;
import com.aw.moviescoll.movie.dto.ErrorResponse;
import com.aw.moviescoll.movie.dto.PopularMoviesDto;
import com.aw.moviescoll.movie.dto.ResourceNotFoundException;
import com.aw.moviescoll.movie.dto.UnauthorizedException;
import com.aw.moviescoll.movie.dto.movie_details.MovieDetailsDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;

@RequestMapping("/movies")
@RestController
class TmdbApiController {

    private final TmdbApiClient tmdbApiClient;

    TmdbApiController(final TmdbApiClient tmdbApiClient) {
        this.tmdbApiClient = tmdbApiClient;
    }

    @GetMapping("/popular")
    public ResponseEntity<PopularMoviesDto> getPopularThisWeek() {
        return ResponseEntity.ok().body(this.tmdbApiClient.getPopularMovies());
    }

    @GetMapping("/search")
    public ResponseEntity<PopularMoviesDto> searchMovie(@RequestParam("query") final String query) {
        return ResponseEntity.ok().body(this.tmdbApiClient.searchMovie(query));
    }

    @GetMapping("/{id}")
    public ResponseEntity<MovieDetailsDto> getMovieDetails(@PathVariable("id") final long id) {
        return ResponseEntity.ok().body(this.tmdbApiClient.getMovieDetails(id));
    }

    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<ErrorResponse> handleUnauthorizedException(final UnauthorizedException ex) {
        final ErrorResponse errorResponse = new ErrorResponse(HttpStatus.UNAUTHORIZED.value(), Instant.now(), ex.getMessage());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED.value()).body(errorResponse);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleResourceNotFoundException(final ResourceNotFoundException ex) {
        final ErrorResponse errorResponse = new ErrorResponse(HttpStatus.NOT_FOUND.value(), Instant.now(), ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND.value()).body(errorResponse);
    }
}
