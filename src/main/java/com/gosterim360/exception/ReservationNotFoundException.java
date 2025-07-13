package com.gosterim360.exception;

import org.springframework.http.HttpStatus;

public class ReservationNotFoundException extends AbstractExceptionHandler {
    public ReservationNotFoundException(String message) {
        super(message, HttpStatus.NOT_FOUND);
    }
}
