package com.aw.moviescoll.movie;

import com.aw.moviescoll.movie.dto.TmdbPopularMoviesDto;
import com.jayway.jsonpath.JsonPath;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
class TmdbApiClient {

    private final WebClient webClient;

    TmdbApiClient(final WebClient webClient) {
        this.webClient = webClient;
    }

    TmdbPopularMoviesDto getPopularMovies() {
        return this.webClient
                .get()
//                .uri("/3/trending/movie/week")
                .uri("/6/trading/movie/week")
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
