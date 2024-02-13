package com.talentmarket.KmongJpa.exception;

import com.talentmarket.KmongJpa.Dto.ApiResponse;
import jakarta.validation.ValidationException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.List;

@RestControllerAdvice
@Slf4j
@RequiredArgsConstructor
public class ExceptionAdvice {
    @Http
    @ExceptionHandler(CustomException.class)
    public ApiResponse DuplicateRegisterException(CustomException exception) {
        log.info(exception.getErrorCode().getMessage());

        return ApiResponse.of(HttpStatus.BAD_REQUEST,exception.getMessage());

    }



    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ApiResponse MethodArgumentNotValidException(MethodArgumentNotValidException exception) {
        log.info(exception.getFieldError().getDefaultMessage());


        return ApiResponse.of(HttpStatus.BAD_REQUEST, exception.getBindingResult().getAllErrors().get(0).getDefaultMessage(),null);

//        System.out.println("==========================");
//        BindingResult bindingResult = exception.getBindingResult();
//        List<FieldError> fieldErrors = bindingResult.getFieldErrors();
//
//        List<ApiResponse> apiResponseList = new ArrayList<>();
//        for (FieldError fieldError : fieldErrors) {
//            ApiResponse apiResponse = ApiResponse.of(HttpStatus.BAD_REQUEST, fieldError.getDefaultMessage(),fieldError.getField(), null);
//
//            apiResponseList.add(apiResponse);
//        }
//return apiResponseList;
    }
}
