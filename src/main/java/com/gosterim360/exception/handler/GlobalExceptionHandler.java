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

    // Movie Exceptions
    @ExceptionHandler(MovieAlreadyExistsException.class)
    public ResponseEntity<BaseResponse<Void>> handleMovieAlreadyExists(MovieAlreadyExistsException exception, HttpServletRequest request) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(
                BaseResponse.failure(
                        exception.getMessage(),
                        HttpStatus.CONFLICT.value(),
                        request.getRequestURI(),
                        List.of(exception.getMessage())
                )
        );
    }

    @ExceptionHandler(MovieNotFoundException.class)
    public ResponseEntity<BaseResponse<Void>> handleMovieNotFound(MovieNotFoundException exception, HttpServletRequest request) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                BaseResponse.failure(
                        exception.getMessage(),
                        HttpStatus.NOT_FOUND.value(),
                        request.getRequestURI(),
                        List.of(exception.getMessage())
                )
        );
    }

    // Salon exceptions
    @ExceptionHandler(SalonNotFoundException.class)
    public ResponseEntity<BaseResponse<Void>> handleSalonNotFound(SalonNotFoundException exception, HttpServletRequest request) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                BaseResponse.failure(
                        exception.getMessage(),
                        HttpStatus.NOT_FOUND.value(),
                        request.getRequestURI(),
                        List.of(exception.getMessage())
                )
        );
    }

    @ExceptionHandler(SalonAlreadyExistsException.class)
    public ResponseEntity<BaseResponse<Void>> handleSalonAlreadyExists(SalonAlreadyExistsException exception, HttpServletRequest request) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(
                BaseResponse.failure(
                        exception.getMessage(),
                        HttpStatus.CONFLICT.value(),
                        request.getRequestURI(),
                        List.of(exception.getMessage())
                )
        );
    }

    @ExceptionHandler(SalonSeatCapacityInvalidException.class)
    public ResponseEntity<BaseResponse<Void>> handleSeatCapacityInvalid(SalonSeatCapacityInvalidException exception, HttpServletRequest request) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                BaseResponse.failure(
                        exception.getMessage(),
                        HttpStatus.BAD_REQUEST.value(),
                        request.getRequestURI(),
                        List.of(exception.getMessage())
                )
        );
    }

    @ExceptionHandler(SalonDeleteNotAllowedException.class)
    public ResponseEntity<BaseResponse<Void>> handleDeleteNotAllowed(SalonDeleteNotAllowedException exception, HttpServletRequest request) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(
                BaseResponse.failure(
                        exception.getMessage(),
                        HttpStatus.FORBIDDEN.value(),
                        request.getRequestURI(),
                        List.of(exception.getMessage())
                )
        );
    }

    @ExceptionHandler(SalonUpdateConflictException.class)
    public ResponseEntity<BaseResponse<Void>> handleUpdateConflict(SalonUpdateConflictException exception, HttpServletRequest request) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(
                BaseResponse.failure(
                        exception.getMessage(),
                        HttpStatus.CONFLICT.value(),
                        request.getRequestURI(),
                        List.of(exception.getMessage())
                )
        );
    }

    // Reservation exceptions
    @ExceptionHandler(ReservationNotFoundException.class)
    public ResponseEntity<BaseResponse<Void>> handleReservationNotFound(ReservationNotFoundException exception, HttpServletRequest request) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                BaseResponse.failure(
                        exception.getMessage(),
                        HttpStatus.NOT_FOUND.value(),
                        request.getRequestURI(),
                        List.of(exception.getMessage())
                )
        );
    }

    @ExceptionHandler(ReservationAlreadyExistsException.class)
    public ResponseEntity<BaseResponse<Void>> handleReservationAlreadyExists(ReservationAlreadyExistsException exception, HttpServletRequest request) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(
                BaseResponse.failure(
                        exception.getMessage(),
                        HttpStatus.CONFLICT.value(),
                        request.getRequestURI(),
                        List.of(exception.getMessage())
                )
        );
    }

    @ExceptionHandler(ReservationSeatUnavailableException.class)
    public ResponseEntity<BaseResponse<Void>> handleReservationSeatUnavailable(ReservationSeatUnavailableException exception, HttpServletRequest request) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(
                BaseResponse.failure(
                        exception.getMessage(),
                        HttpStatus.CONFLICT.value(),
                        request.getRequestURI(),
                        List.of(exception.getMessage())
                )
        );
    }

    @ExceptionHandler(ReservationStatusInvalidException.class)
    public ResponseEntity<BaseResponse<Void>> handleReservationStatusInvalid(ReservationStatusInvalidException exception, HttpServletRequest request) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                BaseResponse.failure(
                        exception.getMessage(),
                        HttpStatus.BAD_REQUEST.value(),
                        request.getRequestURI(),
                        List.of(exception.getMessage())
                )
        );
    }

    @ExceptionHandler(ReservationDeleteNotAllowedException.class)
    public ResponseEntity<BaseResponse<Void>> handleReservationDeleteNotAllowed(ReservationDeleteNotAllowedException exception, HttpServletRequest request) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(
                BaseResponse.failure(
                        exception.getMessage(),
                        HttpStatus.FORBIDDEN.value(),
                        request.getRequestURI(),
                        List.of(exception.getMessage())
                )
        );
    }

    // Generic exception handler
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