package com.gosterim360.exception;

import com.gosterim360.exception.handler.AbstractExceptionHandler;
import org.springframework.http.HttpStatus;

public class UserAlreadyExistsByUsername extends AbstractExceptionHandler {
    public UserAlreadyExistsByUsername(String message) {
        super(message, HttpStatus.CONFLICT);
    }
}
