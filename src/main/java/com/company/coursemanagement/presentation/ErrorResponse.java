package com.company.coursemanagement.presentation;

import java.time.LocalDateTime;

public class ErrorResponse {

    private final String message;
    private final LocalDateTime timestamp;

    private ErrorResponse(String message) {
        this.message = message;
        this.timestamp = LocalDateTime.now();
    }

    public static ErrorResponse of(String message) {
        return new ErrorResponse(message);
    }

    public String getMessage() {
        return message;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }
}