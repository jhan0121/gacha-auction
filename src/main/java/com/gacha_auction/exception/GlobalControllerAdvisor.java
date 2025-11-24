package com.gacha_auction.exception;

import com.gacha_auction.exception.dto.ErrorResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalControllerAdvisor {

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handleNotNullAllowed(final NotNullAllowedException e) {
        final ErrorResponse response = ErrorResponse.from(e.getMessage());
        return ResponseEntity.badRequest().body(response);
    }

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handleNotFound(final NotFoundException e) {
        final ErrorResponse response = ErrorResponse.from(e.getMessage());
        return ResponseEntity.notFound().build();
    }
}
