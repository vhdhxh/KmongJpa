package com.talentmarket.KmongJpa.Dto;

import lombok.Data;

@Data
public class ItemPaginationDto {
    private Long itemId;
    private String title;
    private String name;
    private int price;
}
