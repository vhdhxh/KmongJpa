package com.talentmarket.KmongJpa.Dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CanselRequest {
    private String imp_id;
    private int checksum;
}
