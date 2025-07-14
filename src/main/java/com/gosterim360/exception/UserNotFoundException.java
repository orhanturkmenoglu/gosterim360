package com.gosterim360.exception;

import org.springframework.http.HttpStatus;

public class UserNotFoundException extends AbstractExceptionHandler {
    public UserNotFoundException(String message) {
        super(message, HttpStatus.NOT_FOUND);
    }
}
