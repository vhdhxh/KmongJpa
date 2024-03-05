package com.talentmarket.KmongJpa.Dto;

import lombok.Data;

@Data
public class PaymentRequest {
    private String imp_uid;
    private int amount;
    private String uuid;
}
