package com.aw.moviescoll.movie.dto;

public class UnauthorizedException extends RuntimeException {

    public UnauthorizedException(final String message) {
        super(message);
    }
}
