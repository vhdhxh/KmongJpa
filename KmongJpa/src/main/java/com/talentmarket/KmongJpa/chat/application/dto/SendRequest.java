package com.talentmarket.KmongJpa.chat.application.dto;

import lombok.Data;

@Data
public class SendRequest {
    private Long senderId;
    private Long receiverId;
    private String message;

}
