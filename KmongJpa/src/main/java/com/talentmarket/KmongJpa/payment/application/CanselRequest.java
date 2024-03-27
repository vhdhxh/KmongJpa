package com.talentmarket.KmongJpa.payment.application;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CanselRequest {
    private String imp_id;
    private int checksum;
}
