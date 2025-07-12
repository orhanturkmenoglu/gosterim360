package com.gosterim360.exception;

import com.gosterim360.exception.handler.AbstractExceptionHandler;
import org.springframework.http.HttpStatus;

public class ReservationStatusInvalidException extends AbstractExceptionHandler {
    public ReservationStatusInvalidException(String message) {
        super(message, HttpStatus.BAD_REQUEST);
    }
}
