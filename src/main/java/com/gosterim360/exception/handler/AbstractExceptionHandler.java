package com.gosterim360.exception.handler;

import org.springframework.http.HttpStatus;

public class AbstractExceptionHandler extends RuntimeException{
    private HttpStatus status;
    public AbstractExceptionHandler(String message, HttpStatus status){
        super(message);
        this.status=status;
    }
    public HttpStatus getHttpStatus() {
        return status;
    }

}

