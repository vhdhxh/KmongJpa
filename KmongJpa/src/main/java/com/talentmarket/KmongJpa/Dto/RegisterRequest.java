package com.talentmarket.KmongJpa.Dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class RegisterRequest {
    private String password;
    private String address;
    private String gender;
    private String email;
    private String provider;
    private Long providerId;
    private String image;
}
