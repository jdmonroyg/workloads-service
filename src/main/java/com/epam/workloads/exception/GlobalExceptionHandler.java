package com.epam.workloads.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.LockedException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingRequestHeaderException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

/**
 * @author jdmon on 20/12/2025
 * @project workloads-service
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<List<DataError>> handleException(MethodArgumentNotValidException e) {
        LOGGER.warn("MethodArgumentNotValid exception: {}", e.getMessage());
        List<DataError> errors = e.getFieldErrors().stream().map(DataError::new).toList();
        return ResponseEntity.badRequest().body(errors);
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<String> handleException(NotFoundException e){
        LOGGER.warn("NotFoundException exception: {}", e.getMessage());
        return ResponseEntity.notFound().build();
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> handleException(IllegalArgumentException e){
        LOGGER.warn("IllegalArgumentException exception: {}", e.getMessage());
        return ResponseEntity.notFound().build();
    }

    @ExceptionHandler(MissingRequestHeaderException.class)
    public ResponseEntity<String> handleException(MissingRequestHeaderException e){
        LOGGER.warn("Authentication token is missing: {}", e.getMessage());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
    }

    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<String> handleException(UnauthorizedException e){
        LOGGER.warn("Unauthorized exception: {}", e.getMessage());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
    }

    public record DataError(String field, String error) {
        public DataError(FieldError error) {
            this(error.getField(), error.getDefaultMessage());
        }
    }

}

