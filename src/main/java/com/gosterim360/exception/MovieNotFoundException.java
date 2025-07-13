package com.gosterim360.exception;

import org.springframework.http.HttpStatus;

public class MovieNotFoundException extends AbstractExceptionHandler {
    public MovieNotFoundException(String message) {
        super(message, HttpStatus.NOT_FOUND);
    }
}
