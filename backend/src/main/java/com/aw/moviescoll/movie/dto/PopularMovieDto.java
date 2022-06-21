package com.aw.moviescoll.movie.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.math.BigDecimal;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record PopularMovieDto(int id,
                              boolean adult,
                              @JsonAlias("backdrop_path")
                                  String backdropPath,
                              @JsonAlias("genre_ids")
                                  List<Integer> genreIds,
                              @JsonAlias("original_language")
                                  String originalLanguage,
                              @JsonAlias("original_title")
                                  String originalTitle,
                              String overview,
                              @JsonAlias("poster_path")
                                  String posterPath,
                              @JsonAlias("release_date")
                                  String releaseDate,
                              String title,
                              boolean video,
                              @JsonAlias("vote_average")
                                  BigDecimal voteAverage,
                              @JsonAlias("vote_count")
                                  int voteCount,
                              BigDecimal popularity,
                              @JsonAlias("media_type")
                                  String mediaType) {

    @Override
    public String posterPath() {
        return "https://image.tmdb.org/t/p/w500" + this.posterPath;
    }
}
    
