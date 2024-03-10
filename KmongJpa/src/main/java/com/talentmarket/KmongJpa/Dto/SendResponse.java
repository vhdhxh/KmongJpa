package com.talentmarket.KmongJpa.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SendResponse {
    private String senderName;
    private String message;
}
