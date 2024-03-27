package com.talentmarket.KmongJpa.chat.application.dto;

import lombok.Data;

@Data
public class ChatRequest {

    private Long partnerId;
    private String partnerName;
}
