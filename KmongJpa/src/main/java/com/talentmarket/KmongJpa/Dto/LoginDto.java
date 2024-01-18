package com.talentmarket.KmongJpa.Dto;

import lombok.Getter;

@Getter
public class LoginDto {
    private Long id;
    private String email;
    private String password;
    private String address;
    private String gender;
    private String provider;
    private Long providerId;
}
