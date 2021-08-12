package org.moonsong.booknet.controller;

import org.moonsong.booknet.dto.ErrorResponse;
import org.moonsong.booknet.exception.DomainLogicException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ControllerAdvice {
    private final Logger logger = LoggerFactory.getLogger(ControllerAdvice.class);

    @ExceptionHandler(DomainLogicException.class)
    public ResponseEntity<ErrorResponse> handleCustomExceptions(DomainLogicException exception) {
        logger.info(exception.getMessage());
        return ResponseEntity
                .status(exception.getStatus())
                .body(ErrorResponse.from(exception));
    }
}
