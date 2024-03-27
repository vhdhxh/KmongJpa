package com.talentmarket.KmongJpa.user.application.dto;

import lombok.Data;

@Data
public class FindPasswordRequest {

    private String email;
    private String name;
    private String phone;
}
