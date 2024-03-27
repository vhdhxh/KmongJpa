package com.talentmarket.KmongJpa.sms.application;

import com.talentmarket.KmongJpa.sms.application.SmsMessageDto;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class SmsRequestDto {
    private String type;
    private String contentType;
    private String countryCode;
    private String from;
    private String content;
    private List<SmsMessageDto> messages;
}
