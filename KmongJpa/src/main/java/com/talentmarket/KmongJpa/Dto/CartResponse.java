package com.talentmarket.KmongJpa.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CartResponse {

    private String item;
    private int count;
    private int price;

}
