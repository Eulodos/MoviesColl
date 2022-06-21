package com.aw.moviescoll.movie.domain;

import com.aw.moviescoll.movie.dto.ResourceNotFoundException;
import com.aw.moviescoll.movie.dto.PopularMoviesDto;
import com.aw.moviescoll.movie.dto.UnauthorizedException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.web.reactive.function.client.ClientRequest;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.ExchangeFunction;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@ExtendWith(MockitoExtension.class)
class TmdbApiClientTest {

    @Mock
    private ExchangeFunction exchangeFunction;

    private TmdbApiClient tmdbApiClient;

    @BeforeEach
    void setUp() {
        final WebClient webClient = WebClient.builder()
                .exchangeFunction(exchangeFunction).build();
        this.tmdbApiClient = new TmdbApiClient(webClient);
    }

    @Test
    void shouldFetchPopularMovies() {
        Mockito.when(exchangeFunction.exchange(Mockito.any(ClientRequest.class))).thenReturn(exchangePopular());

        final PopularMoviesDto popularMovies = this.tmdbApiClient.getPopularMovies();

        assertThat(popularMovies.totalPages()).isEqualTo(1);
        assertThat(popularMovies.page()).isEqualTo(1);
        assertThat(popularMovies.totalResults()).isEqualTo(2);
        assertThat(popularMovies.results()).hasSize(2);
    }

    @Test
    void shouldThrowUnauthorizedExceptionIfReturnedStatusIs401() {
        Mockito.when(exchangeFunction.exchange(Mockito.any(ClientRequest.class))).thenReturn(exchangePopularUnauthorizedFailure());


        assertThatThrownBy(() -> this.tmdbApiClient.getPopularMovies()).isInstanceOf(UnauthorizedException.class);
    }

    @Test
    void shouldThrowResourceNotFoundExceptionIfReturnedStatusIs404() {
        Mockito.when(exchangeFunction.exchange(Mockito.any(ClientRequest.class))).thenReturn(exchangePopularNotFoundFailure());

        assertThatThrownBy(() -> this.tmdbApiClient.getPopularMovies()).isInstanceOf(ResourceNotFoundException.class);
    }

    Mono<ClientResponse> exchangePopular() {
        return Mono.just(
                ClientResponse.create(HttpStatus.OK)
                        .header("Content-Type", "application/json")
                        .body("{\"total_results\":2,\"total_pages\":1,\"page\":1,\"results\":[{\"backdrop_path\":\"\",\"genre_ids\":[1,2],\"original_language\":\"IT\",\"original_title\":\"Papa Americano\",\"poster_path\":\"https://image.tmdb.org/t/p/w500\",\"release_date\":\"2034-02-11\",\"vote_average\":10,\"vote_count\":2137,\"media_type\":\"film\",\"id\":1,\"adult\":false,\"overview\":\"Si\",\"title\":\"Papa Americano\",\"video\":false,\"popularity\":1},{\"backdrop_path\":\"\",\"genre_ids\":[6,9],\"original_language\":\"FR\",\"original_title\":\"Foxy Lady\",\"poster_path\":\"https://image.tmdb.org/t/p/w500\",\"release_date\":\"2029-04-19\",\"vote_average\":10,\"vote_count\":420,\"media_type\":\"film\",\"id\":2,\"adult\":true,\"overview\":\"...\",\"title\":\"Foxy Lady\",\"video\":true,\"popularity\":1}]}")
                        .build());
    }

    Mono<ClientResponse> exchangePopularUnauthorizedFailure() {
        return Mono.just(
                ClientResponse.create(HttpStatus.UNAUTHORIZED)
                        .header("Content-Type", "application/json")
                        .body("{\"status_code\":7,\"status_message\":\"Invalid API key: You must be granted a valid key.\",\"success\":false}")
                        .build());
    }

    Mono<ClientResponse> exchangePopularNotFoundFailure() {
        return Mono.just(
                ClientResponse.create(HttpStatus.NOT_FOUND)
                        .header("Content-Type", "application/json")
                        .body("NOT FOUND")
                        .build());
    }
}
