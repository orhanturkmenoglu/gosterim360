package com.gosterim360.exception;

import com.gosterim360.exception.handler.AbstractExceptionHandler;
import org.springframework.http.HttpStatus;

public class SalonAlreadyExistsException extends AbstractExceptionHandler {

    public SalonAlreadyExistsException(String message) {
        super(message, HttpStatus.CONFLICT);
    }
}
