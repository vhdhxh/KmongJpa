package com.talentmarket.KmongJpa.auth.application.dto;

import lombok.Data;

@Data
public class KakaoTokenDto {
    private String token_type;
    private String access_token;
    private int id_token;
    private String expires_in;
    private String refresh_token;
    private int refresh_token_expires_in;
    private String scope;
}
