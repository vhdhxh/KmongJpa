package com.talentmarket.KmongJpa.cart.application.dto;

import lombok.Data;

@Data
public class CartRequest {
    private int count;
    private Long itemId;
}
