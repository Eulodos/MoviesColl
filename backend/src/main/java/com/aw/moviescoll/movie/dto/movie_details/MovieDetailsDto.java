package com.aw.moviescoll.movie.dto.movie_details;

import com.fasterxml.jackson.annotation.JsonAlias;

import java.util.List;

public record MovieDetailsDto(
        boolean adult,
        @JsonAlias("backdrop_path")
        String backdropPath,
        @JsonAlias("belongs_to_collection")
        Object belongsToCollection,
        int budget,
        List<MovieGenreDto> genres,
        String homepage,
        int id,
        @JsonAlias("imdb_id")
        String imdbId,
        @JsonAlias("original_language")
        String originalLanguage,
        @JsonAlias("original_title")
        String originalTitle,
        String overview,
        double popularity,
        @JsonAlias("poster_path")
        Object posterPath,
        @JsonAlias("production_companies")
        List<ProductionCompanyDto> productionCompanies,
        @JsonAlias("production_countries")
        List<ProductionCountry> productionCountries,
        @JsonAlias("release_date")
        String releaseDate,
        int revenue,
        int runtime,
        @JsonAlias("spoken_languages")
        List<SpokenLanguage> spokenLanguages,
        String status,
        String tagline,
        String title,
        boolean video,
        @JsonAlias("vote_average")
        double voteAverage,
        @JsonAlias("vote_count")
        int voteCount
) {
}
