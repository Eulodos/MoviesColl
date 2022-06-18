package com.aw.moviescoll.movie;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class TmdbConfig {

    @Bean
    WebClient webClient(@Value("${tmdb.api.key}") final String apiKey, final WebClient.Builder builder) {
        return builder.baseUrl("https://api.themoviedb.org")
                .defaultHeader("Authorization", "Bearer " + apiKey)
                .build();
    }
}
