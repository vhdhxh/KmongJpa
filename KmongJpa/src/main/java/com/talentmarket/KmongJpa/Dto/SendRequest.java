package com.talentmarket.KmongJpa.Dto;

import lombok.Data;

@Data
public class SendRequest {
    private Long senderId;
    private Long receiverId;
    private String message;

}
