package com.talentmarket.KmongJpa.chat.application.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ChatResponse {
    private Long roomId;
    private String receiverNickname;
    private String senderNickname;
    private String message;
    private String sendTime;
    private String userImage;
}
