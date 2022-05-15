package com.aw.moviescoll;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class TmdbService implements MoviesService  {
    private final WebClient webClient;

    TmdbService(WebClient.Builder builder) {
        webClient = builder.baseUrl("https://api.themoviedb.org")
                .defaultHeader("Authorization", "Bearer TOKEN")
                .build();
    }

    public Mono<String> something() {
        return this.webClient
                .get()
                .uri("/3/movie/550")
                .retrieve()
                .bodyToMono(String.class);
    }

    @Override
    public Object getMoviesList() {
        return this.webClient
                .get()
                .uri("/3/movie/popular")
                .retrieve()
                .bodyToMono(String.class);
    }
}
