package com.talentmarket.KmongJpa.Dto;

import lombok.Data;

@Data
public class CartRequest {
    private int count;
    private Long boardId;
}
