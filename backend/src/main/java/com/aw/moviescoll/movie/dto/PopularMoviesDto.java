package com.aw.moviescoll.movie.dto;

import com.fasterxml.jackson.annotation.JsonAlias;

import java.util.List;

public record PopularMoviesDto(
        int page,
        List<PopularMovieDto> results,
        @JsonAlias("total_results")
        int totalResults,
        @JsonAlias("total_pages")
        int totalPages
) {

}
