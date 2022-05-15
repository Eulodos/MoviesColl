package com.aw.moviescoll;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
class TmdbApiController {

    private final TmdbService tmdbService;

    public TmdbApiController(TmdbService tmdbService) {
        this.tmdbService = tmdbService;
    }

    @GetMapping("/")
    public Object something() {
        return this.tmdbService.getMoviesList();
    }
}
