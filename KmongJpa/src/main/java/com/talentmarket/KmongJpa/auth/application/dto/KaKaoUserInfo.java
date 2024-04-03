package com.talentmarket.KmongJpa.auth.application.dto;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Data
@Builder
public class KaKaoUserInfo implements Serializable {
    private String email;
    private String name;
    private Long id;
}
