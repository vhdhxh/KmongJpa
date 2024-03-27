package com.talentmarket.KmongJpa.global.exception;

import lombok.Getter;

@Getter
public class CustomException extends RuntimeException {
    private ErrorCode errorCode;
    private String Message;

    public CustomException(ErrorCode errorCode){
        this.errorCode = errorCode;
        this.Message = errorCode.getMessage();


    }



}
