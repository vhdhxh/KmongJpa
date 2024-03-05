package com.talentmarket.KmongJpa.Dto;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class ResponsePaymentDto {
    private String code;
    private String message;
    private ResponsePayment response;

    @Data
    public static class ResponsePayment {
        private String name;
        private int amount;
    }
}
