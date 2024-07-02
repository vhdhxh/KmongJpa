package com.talentmarket.KmongJpa.Item.application.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class SearchDto {
    String sort;
    String title;
    String category;
}
