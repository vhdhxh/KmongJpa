package com.talentmarket.KmongJpa.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    EMAIL_DUPLICATED(HttpStatus.BAD_REQUEST,"중복된 이메일입니다.");



    private HttpStatus httpStatus;
    private String message;
}
