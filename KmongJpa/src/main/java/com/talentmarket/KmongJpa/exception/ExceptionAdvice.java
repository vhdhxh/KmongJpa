package com.talentmarket.KmongJpa.exception;

import jakarta.validation.ValidationException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
@RequiredArgsConstructor
public class ExceptionAdvice {
    @ExceptionHandler(CustomException.class)
    public ResponseEntity DuplicateRegisterException(CustomException exception) {
        log.info(exception.getMessage());

        return ResponseEntity.status(exception.getErrorCode().getHttpStatus()).body(exception.getErrorCode().getMessage());
    }
}
