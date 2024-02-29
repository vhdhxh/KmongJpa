package com.talentmarket.KmongJpa.Dto;

import com.talentmarket.KmongJpa.entity.Item;
import lombok.*;

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
