package com.gaelalburo.hackingmx25.autoahorroapi.exceptions;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.stream.Collectors;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationErrors(MethodArgumentNotValidException ex, HttpServletRequest request) {
        String msg = ex.getBindingResult().getFieldErrors()
                .stream()
                .map(error -> error.getField() + ": " + error.getDefaultMessage())
                .collect(Collectors.joining("; "));

        log.error("Validation failed: {}", msg);
        return buildError(HttpStatus.BAD_REQUEST, "Validation failed", msg, request.getRequestURI());
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorResponse> handleUnreadableMessage(HttpMessageNotReadableException ex, HttpServletRequest request) {
        String msg = "Malformed JSON or invalid date format";
        log.error("Malformed JSON: {}", ex.getMostSpecificCause().getMessage());
        return buildError(HttpStatus.BAD_REQUEST, "Bad Request", msg, request.getRequestURI());
    }

    @ExceptionHandler(InvalidFormatException.class)
    public ResponseEntity<ErrorResponse> handleInvalidFormat(InvalidFormatException ex, HttpServletRequest request) {
        String msg = "Invalid format for field: " + ex.getPathReference();
        return buildError(HttpStatus.BAD_REQUEST, "Invalid format", msg, request.getRequestURI());
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ErrorResponse> handleConstraintViolation(ConstraintViolationException ex, HttpServletRequest request) {
        String msg = ex.getMessage();
        log.error("Validation failed: {}", msg);
        return buildError(HttpStatus.BAD_REQUEST, "Validation failed", msg, request.getRequestURI());
    }

    private ResponseEntity<ErrorResponse> buildError(HttpStatus status, String error, String message, String path) {
        ErrorResponse apiError = new ErrorResponse(
                status.value(),
                error,
                message,
                path,
                LocalDateTime.now()
        );
        return new ResponseEntity<>(apiError, status);
    }
}