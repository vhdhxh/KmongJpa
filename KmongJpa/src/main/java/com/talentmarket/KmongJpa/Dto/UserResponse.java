package com.talentmarket.KmongJpa.Dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserResponse {
    private String email;
    private String name;
    private String image;
}
