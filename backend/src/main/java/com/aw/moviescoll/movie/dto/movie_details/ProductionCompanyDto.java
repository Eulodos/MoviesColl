package com.aw.moviescoll.movie.dto.movie_details;

import com.fasterxml.jackson.annotation.JsonAlias;

public record ProductionCompanyDto(int id,
                                   @JsonAlias("logo_path") String logoPath,
                                   String name,
                                   @JsonAlias("origin_country") String originCountry) {
}
