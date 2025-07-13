package com.gosterim360.exception;

import org.springframework.http.HttpStatus;

public class SalonAlreadyExistsException extends AbstractExceptionHandler {

    public SalonAlreadyExistsException(String message) {
        super(message, HttpStatus.CONFLICT);
    }
}
