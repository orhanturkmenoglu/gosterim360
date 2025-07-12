package com.gosterim360.exception;

public class UserAlreadyExistsByUsername extends RuntimeException {
    public UserAlreadyExistsByUsername(String message) {
        super(message);
    }
}
