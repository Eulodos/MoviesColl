package com.aw.moviescoll;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
class TmdbApiController {

    private final TmdbService tmdbService;

    TmdbApiController(final TmdbService tmdbService) {
        this.tmdbService = tmdbService;
    }

    @GetMapping("/")
    public Object something() {
        return this.tmdbService.getMoviesList();
    }
}
