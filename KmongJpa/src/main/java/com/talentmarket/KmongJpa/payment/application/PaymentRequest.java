package com.talentmarket.KmongJpa.payment.application;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PaymentRequest {
    private String imp_uid;
    private int amount;
    private String uuid;
}
