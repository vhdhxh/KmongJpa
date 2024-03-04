package com.talentmarket.KmongJpa.Dto;

import lombok.Data;

@Data
public class TokenResponseDto {
    private TokenResponse response;

    public static class TokenResponse {
        private String access_token;
        private Long now;
        private Long expired_at;
    }
}
