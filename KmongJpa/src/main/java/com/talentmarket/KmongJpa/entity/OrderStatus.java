package com.talentmarket.KmongJpa.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter

public enum OrderStatus {
    Try,Success,Fail;



    private String message;
}
