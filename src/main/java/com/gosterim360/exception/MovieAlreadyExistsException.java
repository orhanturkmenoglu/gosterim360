package com.gosterim360.exception;

import com.gosterim360.exception.handler.AbstractExceptionHandler;
import org.springframework.http.HttpStatus;

public class MovieAlreadyExistsException extends AbstractExceptionHandler {
    public MovieAlreadyExistsException(String message) {
        super(message, HttpStatus.CONFLICT);
    }
}
