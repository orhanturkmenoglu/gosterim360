package com.gosterim360.exception;

import com.gosterim360.exception.handler.AbstractExceptionHandler;
import org.springframework.http.HttpStatus;

public class SalonNotFoundException extends AbstractExceptionHandler {

    public SalonNotFoundException(String message) {
        super(message, HttpStatus.NOT_FOUND);
    }
}
