package com.gosterim360.exception;

import org.springframework.http.HttpStatus;

public class SalonUpdateConflictException extends AbstractExceptionHandler {
    public SalonUpdateConflictException(String message) {
        super(message, HttpStatus.CONFLICT);
    }
}
