package com.talentmarket.KmongJpa.order.application;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter

public enum OrderStatus {
    Try,Success,Fail, cancelFail;



    private String message;
}
