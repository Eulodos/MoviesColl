package com.aw.moviescoll.movie.domain;

import com.aw.moviescoll.movie.dto.ResourceNotFoundException;
import com.aw.moviescoll.movie.dto.TmdbPopularMoviesDto;
import com.aw.moviescoll.movie.dto.UnauthorizedException;
import com.jayway.jsonpath.JsonPath;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class TmdbApiClient {

    private final WebClient webClient;

    public TmdbApiClient(final WebClient webClient) {
        this.webClient = webClient;
    }

    public TmdbPopularMoviesDto getPopularMovies() {
        return this.webClient
                .get()
                .uri("/3/trending/movie/week")
                .retrieve()
                .onStatus(httpStatus -> httpStatus.value() == 401, clientResponse -> clientResponse.bodyToMono(String.class).flatMap(s -> {
                    String originalMessage = JsonPath.read(s, "$.status_message");
                    return Mono.error(new UnauthorizedException(originalMessage));
                }))
                .onStatus(httpStatus -> httpStatus.value() == 404, clientResponse -> Mono.error(new ResourceNotFoundException("Requested resource could not be found")))
                .bodyToMono(TmdbPopularMoviesDto.class)
                .block();
    }

}
