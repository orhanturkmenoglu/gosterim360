package com.gosterim360.exception;

import org.springframework.http.HttpStatus;

public class ReservationSeatUnavailableException extends AbstractExceptionHandler {
    public ReservationSeatUnavailableException(String message) {
        super(message, HttpStatus.CONFLICT);
    }
}
