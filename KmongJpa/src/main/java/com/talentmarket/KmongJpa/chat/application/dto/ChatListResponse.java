package com.talentmarket.KmongJpa.chat.application.dto;


import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ChatListResponse {
    private Long roomId;
    private String otherNickname;
    private String recentMessage;
    private String recentTime;
    private String userImage;
}
