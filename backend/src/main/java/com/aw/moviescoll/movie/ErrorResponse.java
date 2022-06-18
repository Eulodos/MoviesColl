package com.aw.moviescoll.movie;

import java.time.Instant;

public class ErrorResponse {
    private int status;
    private Instant timestamp;
    private String message;

    public ErrorResponse(int status, Instant timestamp, String message) {
        this.status = status;
        this.timestamp = timestamp;
        this.message = message;
    }

    public int getStatus() {
        return status;
    }

    public Instant getTimestamp() {
        return timestamp;
    }

    public String getMessage() {
        return message;
    }
}
