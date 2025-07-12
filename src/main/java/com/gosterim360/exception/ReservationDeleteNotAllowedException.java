package com.gosterim360.exception;

import com.gosterim360.exception.handler.AbstractExceptionHandler;
import org.springframework.http.HttpStatus;

public class ReservationDeleteNotAllowedException extends AbstractExceptionHandler {
    public ReservationDeleteNotAllowedException(String message) {
        super(message, HttpStatus.FORBIDDEN);
    }
}
