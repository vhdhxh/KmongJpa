package com.talentmarket.KmongJpa.Dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class BoardPagingResponse {
    public String thumbnail;
    public String title;
    public String price;
    public String writer;
}
