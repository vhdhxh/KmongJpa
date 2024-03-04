package com.talentmarket.KmongJpa.Dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TokenRequestDto {
    private String imp_key;
    private String imp_secret;
}
