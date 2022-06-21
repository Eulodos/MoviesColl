package com.aw.moviescoll.movie.domain;

import com.aw.moviescoll.movie.dto.ResourceNotFoundException;
import com.aw.moviescoll.movie.dto.PopularMoviesDto;
import com.aw.moviescoll.movie.dto.UnauthorizedException;
import com.aw.moviescoll.movie.dto.movie_details.MovieDetailsDto;
import com.jayway.jsonpath.JsonPath;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.function.Function;

@Service
public class TmdbApiClient {

    private final WebClient webClient;

    public TmdbApiClient(final WebClient webClient) {
        this.webClient = webClient;
    }

    public PopularMoviesDto getPopularMovies() {
        return this.webClient
                .get()
                .uri("/3/trending/movie/week")
                .retrieve()
                .onStatus(httpStatus -> httpStatus.value() == HttpStatus.UNAUTHORIZED.value(), this::handleUnauthorizedStatus)
                .onStatus(httpStatus -> httpStatus.value() == HttpStatus.NOT_FOUND.value(), handleNotFoundStatus())
                .bodyToMono(PopularMoviesDto.class)
                .block();
    }

    public PopularMoviesDto searchMovie(final String query) {
        return this.webClient
                .get()
                .uri(uriBuilder -> uriBuilder.path("/3/search/movie")
                        .queryParam("query", query)
                        .build())
                .retrieve()
                .onStatus(httpStatus -> httpStatus.value() == HttpStatus.UNAUTHORIZED.value(), this::handleUnauthorizedStatus)
                .onStatus(httpStatus -> httpStatus.value() == HttpStatus.NOT_FOUND.value(), handleNotFoundStatus())
                .bodyToMono(PopularMoviesDto.class)
                .block();
    }

    public MovieDetailsDto getMovieDetails(final long movieId) {
        return this.webClient
                .get()
                .uri(uriBuilder -> uriBuilder.path("/3/movie/{movieId}")
                        .build(movieId))
                .retrieve()
                .onStatus(httpStatus -> httpStatus.value() == HttpStatus.UNAUTHORIZED.value(), this::handleUnauthorizedStatus)
                .onStatus(httpStatus -> httpStatus.value() == HttpStatus.NOT_FOUND.value(), handleNotFoundStatus())
                .bodyToMono(MovieDetailsDto.class)
                .block();
    }

    private Mono<Throwable> handleUnauthorizedStatus(ClientResponse clientResponse) {
        return clientResponse.bodyToMono(String.class).flatMap(s -> {
            final String originalMessage = JsonPath.read(s, "$.status_message");
            return Mono.error(new UnauthorizedException(originalMessage));
        });
    }

    private Function<ClientResponse, Mono<? extends Throwable>> handleNotFoundStatus() {
        return clientResponse -> Mono.error(new ResourceNotFoundException("Requested resource could not be found"));
    }
}
