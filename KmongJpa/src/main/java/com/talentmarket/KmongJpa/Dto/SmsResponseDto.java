package com.talentmarket.KmongJpa.Dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class SmsResponseDto {
    private String requestId;
    private LocalDateTime requestTime;
    private String statusCode;
    private String statusName;
    private String smsConfirmNum;

    public SmsResponseDto(String smsConfirmNum) {
        this.smsConfirmNum=smsConfirmNum;
    }
}
