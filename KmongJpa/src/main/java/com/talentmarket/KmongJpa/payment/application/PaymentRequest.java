package com.talentmarket.KmongJpa.payment.application;

import lombok.Data;

@Data
public class PaymentRequest {
    private String imp_uid;
    private int amount;
    private String uuid;
}
