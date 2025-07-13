package com.gosterim360.exception;

import org.springframework.http.HttpStatus;

public class UserAlreadyExistsByUsername extends AbstractExceptionHandler {
    public UserAlreadyExistsByUsername(String message) {
        super(message, HttpStatus.CONFLICT);
    }
}
