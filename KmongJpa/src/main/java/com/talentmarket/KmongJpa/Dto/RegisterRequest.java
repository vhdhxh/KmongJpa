package com.talentmarket.KmongJpa.Dto;

import lombok.*;

@Getter
@AllArgsConstructor
@NoArgsConstructor //역직렬화 과정시 objectmapper가 기본생성자 필요
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
