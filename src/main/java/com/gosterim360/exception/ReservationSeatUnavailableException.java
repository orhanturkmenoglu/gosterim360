package com.gosterim360.exception;

import com.gosterim360.exception.handler.AbstractExceptionHandler;
import org.springframework.http.HttpStatus;

public class ReservationSeatUnavailableException extends AbstractExceptionHandler {
    public ReservationSeatUnavailableException(String message) {
        super(message, HttpStatus.CONFLICT);
    }
}
