package com.talentmarket.KmongJpa.Item.application.dto;

import com.talentmarket.KmongJpa.Item.domain.Item;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WriteRequest {
    private String title;
    private String contents;
    private String thumbnail;
    private int price;
    private String detail;
    private List<String> images;

    public static WriteRequest ToDto(Item item) {
        return WriteRequest
                .builder()
                .contents(item.getContents())
                .title(item.getTitle())
                .thumbnail(item.getThumbnail())
                .price(item.getPrice())
                .detail(item.getDetail())
                .build();
    }
}
