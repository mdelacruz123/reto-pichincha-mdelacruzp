package com.experience.api.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.Map;

@RestControllerAdvice
public class CustomExceptionHandler {

    @ExceptionHandler(UserNotFoundException.class)
    public Map<String, Object> handleUserNotFound(UserNotFoundException ex) {
        return Map.of(
                "timestamp", LocalDateTime.now(),
                "status", HttpStatus.NOT_FOUND.value(),
                "error", "User Not Found",
                "message", ex.getMessage()
        );
    }

    @ExceptionHandler(ExchangeRateNotFoundException.class)
    public Map<String, Object> handleExchangeRateNotFound(ExchangeRateNotFoundException ex) {
        return Map.of(
                "timestamp", LocalDateTime.now(),
                "status", HttpStatus.NOT_FOUND.value(),
                "error", "Exchange Rate Not Found",
                "message", ex.getMessage()
        );
    }
}
