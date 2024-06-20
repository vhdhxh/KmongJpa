package com.talentmarket.KmongJpa.auth.application.dto;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class LoginResponse {
    private String userEmail;
    private String userName;
    private String userImage;
}
