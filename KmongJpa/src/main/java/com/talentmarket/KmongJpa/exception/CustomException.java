package com.talentmarket.KmongJpa.exception;

import lombok.Getter;

@Getter
public class CustomException extends RuntimeException {
    private ErrorCode errorCode;

    public CustomException(ErrorCode errorCode){
        this.errorCode=errorCode;
        super("dd");
    }


}
