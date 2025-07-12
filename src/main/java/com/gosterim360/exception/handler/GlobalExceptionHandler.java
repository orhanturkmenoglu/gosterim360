package com.gosterim360.exception.handler;

import com.gosterim360.common.BaseResponse;
import com.gosterim360.exception.*;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<BaseResponse<Void>> handleValidationExceptions(MethodArgumentNotValidException exception,
                                                                         HttpServletRequest request) {
        List<String> errorMessages = exception.getBindingResult().getFieldErrors()
                .stream()
                .map(error -> error.getField() + ": " + error.getDefaultMessage())
                .toList();

        return ResponseEntity.badRequest().body(
                BaseResponse.failure(
                        "VALIDATION FAILED",
                        HttpStatus.BAD_REQUEST.value(),
                        request.getRequestURI(),
                        errorMessages
                )
        );
    }

    @ExceptionHandler(AbstractExceptionHandler.class)
    public ResponseEntity<BaseResponse<Void>> handleMovieAlreadyExists(AbstractExceptionHandler exception, HttpServletRequest request) {
        return ResponseEntity.status(exception.getHttpStatus()).body(
                BaseResponse.failure(
                        exception.getMessage(),
                        exception.getHttpStatus().value(),
                        request.getRequestURI(),
                        List.of(exception.getMessage())
                )
        );
    }




    @ExceptionHandler(Exception.class)
    public ResponseEntity<BaseResponse<Void>> handleGenericException(Exception exception, HttpServletRequest request) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                BaseResponse.failure(
                        "Internal server error",
                        HttpStatus.INTERNAL_SERVER_ERROR.value(),
                        request.getRequestURI(),
                        List.of(exception.getMessage())
                )
        );
    }
}