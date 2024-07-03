package com.talentmarket.KmongJpa.Item.application.dto;

import lombok.Data;

@Data
public class ItemPaginationDto {
    private Long itemId;
    private String title;
    private String name;
    private int price;
    private String category;
}
