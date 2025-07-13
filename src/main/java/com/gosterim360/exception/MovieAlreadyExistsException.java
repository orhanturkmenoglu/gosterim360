package com.gosterim360.exception;

import org.springframework.http.HttpStatus;

public class MovieAlreadyExistsException extends AbstractExceptionHandler {
    public MovieAlreadyExistsException(String message) {
        super(message, HttpStatus.CONFLICT);
    }
}
