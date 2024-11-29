package com.support.api.exception;

import com.support.api.model.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BadRequestException.class)
    public Mono<Void> handleBadRequestException(BadRequestException ex, ServerWebExchange exchange) {
        return buildErrorResponse(exchange, HttpStatus.BAD_REQUEST, "Bad Request", ex.getMessage());
    }

    @ExceptionHandler(NotFoundException.class)
    public Mono<Void> handleNotFoundException(NotFoundException ex, ServerWebExchange exchange) {
        return buildErrorResponse(exchange, HttpStatus.INTERNAL_SERVER_ERROR, "Not Found", ex.getMessage());
    }

    private Mono<Void> buildErrorResponse(ServerWebExchange exchange, HttpStatus status, String error, String message) {
        exchange.getResponse().setStatusCode(status);
        exchange.getResponse().getHeaders().setContentType(MediaType.APPLICATION_JSON);

        ErrorResponse errorResponse = new ErrorResponse(
                LocalDateTime.now(),
                status.value(),
                error,
                message
        );
        return exchange.getResponse().writeWith(
                Mono.just(exchange.getResponse().bufferFactory().wrap(toJson(errorResponse).getBytes()))
        );
    }

    private String toJson(ErrorResponse errorResponse) {
        return String.format(
                "{\"timestamp\": \"%s\", \"status\": %d, \"error\": \"%s\", \"message\": \"%s\"}",
                errorResponse.getTimestamp(),
                errorResponse.getStatus(),
                errorResponse.getError(),
                errorResponse.getMessage()
        );
    }
}