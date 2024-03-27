package com.talentmarket.KmongJpa.Item.application.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ItemPagingResponse {


    public Long itemId;
    public String thumbnail;
    public String title;
    public int price;
    public String writer;

}
