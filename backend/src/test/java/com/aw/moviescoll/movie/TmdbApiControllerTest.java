package com.aw.moviescoll.movie;

import com.aw.moviescoll.movie.dto.TmdbPopularMovieDto;
import com.aw.moviescoll.movie.dto.TmdbPopularMoviesDto;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.math.BigDecimal;
import java.util.List;

import static org.mockito.BDDMockito.given;

@WebMvcTest(TmdbApiController.class)
class TmdbApiControllerTest {

    @MockBean
    private TmdbApiClient tmdbApiClient;

    @Autowired
    private WebTestClient webTestClient;

    @Test
    void shouldReturnHttpOkWhenThereAreNoErrors() {
        TmdbPopularMovieDto firstMovie = new TmdbPopularMovieDto(1, false, "", List.of(1, 2), "IT", "Papa Americano", "Si", "", "2034-02-11", "Papa Americano", false, BigDecimal.TEN, 2137, BigDecimal.ONE, "film");
        TmdbPopularMovieDto secondMovie = new TmdbPopularMovieDto(2, true, "", List.of(6, 9), "FR", "Foxy Lady", "...", "", "2029-04-19", "Foxy Lady", true, BigDecimal.TEN, 420, BigDecimal.ONE, "film");
        TmdbPopularMoviesDto popularMoviesDto = new TmdbPopularMoviesDto(1, List.of(firstMovie, secondMovie), 2, 1);

        given(tmdbApiClient.getPopularMovies())
                .willReturn(popularMoviesDto);

        webTestClient.get()
                .uri("/movies/popular")
                .exchange()
                .expectStatus().isOk()
                .expectBody().jsonPath("$.results", Matchers.hasSize(2));
    }

    @Test
    void shouldReturnHttpUnauthorizedWhenApiKeyIsMissing() {
        given(tmdbApiClient.getPopularMovies()).willThrow(new UnauthorizedException("Invalid API key"));

        webTestClient.get()
                .uri("/movies/popular")
                .exchange()
                .expectStatus().isUnauthorized()
                .expectBody()
                .jsonPath("$.message").isEqualTo("Invalid API key")
                .jsonPath("$.status").isEqualTo(401)
                .jsonPath("$.timestamp").exists();
    }
}