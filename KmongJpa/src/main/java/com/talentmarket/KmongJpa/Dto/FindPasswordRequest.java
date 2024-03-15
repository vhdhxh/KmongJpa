package com.talentmarket.KmongJpa.Dto;

import lombok.Data;

@Data
public class FindPasswordRequest {

    private String email;
    private String name;
    private String phone;
}
