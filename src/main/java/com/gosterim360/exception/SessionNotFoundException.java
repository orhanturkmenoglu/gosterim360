package com.gosterim360.exception;

import org.springframework.http.HttpStatus;

public class SessionNotFoundException extends AbstractExceptionHandler {

    public SessionNotFoundException(String message) {
        super(message, HttpStatus.NOT_FOUND);
    }
}
