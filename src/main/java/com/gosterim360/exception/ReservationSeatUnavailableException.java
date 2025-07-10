package com.gosterim360.exception;

public class ReservationSeatUnavailableException extends RuntimeException {
    public ReservationSeatUnavailableException(String message) {
        super(message);
    }
}
