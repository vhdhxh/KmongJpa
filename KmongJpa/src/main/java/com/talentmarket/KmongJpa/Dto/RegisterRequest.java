package com.talentmarket.KmongJpa.Dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@AllArgsConstructor
@Builder
@Setter
public class RegisterRequest {
    private String password;
    private String address;
    private String gender;
    private String email;
    private String provider;
    private String name;
    private Long providerId;
}
