package com.talentmarket.KmongJpa.exception;

import lombok.Getter;
import org.aspectj.bridge.Message;

@Getter
public class CustomException extends RuntimeException {
    private ErrorCode errorCode;
    private String Message;

    public CustomException(ErrorCode errorCode){
        this.errorCode = errorCode;
        this.Message = errorCode.getMessage();


    }



}
