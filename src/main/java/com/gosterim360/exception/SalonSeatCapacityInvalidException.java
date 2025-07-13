package com.gosterim360.exception;

import org.springframework.http.HttpStatus;

public class SalonSeatCapacityInvalidException extends AbstractExceptionHandler {
    public SalonSeatCapacityInvalidException(String message) {
        super(message, HttpStatus.BAD_REQUEST);
    }
}
