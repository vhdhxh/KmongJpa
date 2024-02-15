package com.talentmarket.KmongJpa.Dto;

import com.talentmarket.KmongJpa.entity.Board;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DetailResponse {
    private String title;
    private String contents;
    private String thumbnail;
    private String price;
    private String detail;
    private String writer;

    public static DetailResponse ToDto(Board board) {
        return DetailResponse
                .builder()
                .contents(board.getContents())
                .title(board.getTitle())
                .thumbnail(board.getThumbnail())
                .price(board.getPrice())
                .detail(board.getDetail())
                .writer(board.getUsers().getName())
                .build();
    }
}
