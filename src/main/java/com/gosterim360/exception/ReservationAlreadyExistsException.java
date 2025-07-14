package com.gosterim360.exception;

import org.springframework.http.HttpStatus;

public class ReservationAlreadyExistsException extends AbstractExceptionHandler {
    public ReservationAlreadyExistsException(String message) {
        super(message, HttpStatus.CONFLICT);
    }
}
