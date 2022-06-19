package com.aw.moviescoll.movie.dto;

import java.time.Instant;

public record ErrorResponse(int status, Instant timestamp, String message) {
}
