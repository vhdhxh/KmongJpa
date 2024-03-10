package com.talentmarket.KmongJpa.Dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class BoardPagingResponse {
    public Long itemId;
    public String thumbnail;
    public String title;
    public int price;
    public String writer;
}
