package com.gosterim360.exception;

import org.springframework.http.HttpStatus;

public class SalonNotFoundException extends AbstractExceptionHandler {

    public SalonNotFoundException(String message) {
        super(message, HttpStatus.NOT_FOUND);
    }
}
