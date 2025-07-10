package com.gosterim360.exception;

public class ReservationDeleteNotAllowedException extends RuntimeException {
    public ReservationDeleteNotAllowedException(String message) {
        super(message);
    }
}
