package com.gosterim360.exception;

import org.springframework.http.HttpStatus;

public class SalonDeleteNotAllowedException extends AbstractExceptionHandler {
    public SalonDeleteNotAllowedException(String message) {
        super(message, HttpStatus.FORBIDDEN);
    }
}
