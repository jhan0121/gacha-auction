package com.gacha_auction.exception;

import com.gacha_auction.exception.dto.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class GlobalControllerAdvisor {

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handleNotFoundException(final NotFoundException e) {
        final ErrorResponse response = ErrorResponse.from(e.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND.value()).body(response);
    }

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handleDomainException(final DomainException e) {
        final ErrorResponse response = ErrorResponse.from(e.getMessage());
        return ResponseEntity.badRequest().body(response);
    }

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handleException(final Exception e) {
        final ErrorResponse response = ErrorResponse.from("서버에서 에러가 발생했습니다.");
        log.error("서버 내부 에러 발생 원인: {}", e.getMessage(), e);
        return ResponseEntity.internalServerError().body(response);
    }
}
