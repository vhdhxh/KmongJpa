package com.talentmarket.KmongJpa.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    EMAIL_DUPLICATED(HttpStatus.BAD_REQUEST,"중복된 이메일입니다."),
    USER_WITHDRAWLED(HttpStatus.BAD_REQUEST,"이미 탈퇴된 회원입니다."),
    USER_NOTLOGIN(HttpStatus.BAD_REQUEST,"로그인이 필요합니다.");

    private HttpStatus httpStatus;
    private String message;
}
