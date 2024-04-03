package com.talentmarket.KmongJpa.auth.application.dto;

import lombok.Data;

@Data
public class KakaoRequest {
    private String code;
    private String error;
    private String error_description;
    private String state;
}
