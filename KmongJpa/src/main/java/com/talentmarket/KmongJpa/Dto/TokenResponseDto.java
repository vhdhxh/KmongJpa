package com.talentmarket.KmongJpa.Dto;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class TokenResponseDto {
    private TokenResponse response;
    private int code;
    private String message;

    @Data
    public static class TokenResponse {
        private String access_token;
        private Long now;
        private Long expired_at;
    }
}
