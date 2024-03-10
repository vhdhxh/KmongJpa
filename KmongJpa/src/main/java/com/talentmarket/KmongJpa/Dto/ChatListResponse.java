package com.talentmarket.KmongJpa.Dto;


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
