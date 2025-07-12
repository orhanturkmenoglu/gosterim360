package com.gosterim360.exception;

import com.gosterim360.exception.handler.AbstractExceptionHandler;
import org.springframework.http.HttpStatus;

public class MovieNotFoundException extends AbstractExceptionHandler {
    public MovieNotFoundException(String message) {
        super(message, HttpStatus.NOT_FOUND);
    }
}
