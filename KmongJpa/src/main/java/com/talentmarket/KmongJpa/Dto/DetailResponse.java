package com.talentmarket.KmongJpa.Dto;

import com.talentmarket.KmongJpa.entity.Board;
import com.talentmarket.KmongJpa.entity.Comment;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

@Data
@Builder
public class DetailResponse {
    private String title;
    private String contents;
    private String thumbnail;
    private String price;
    private String detail;
    private String writer;
    private List<CommentDto> commentContents;



    public static DetailResponse ToDto(Board board) {
        return DetailResponse
                .builder()
                .contents(board.getContents())
                .title(board.getTitle())
                .thumbnail(board.getThumbnail())
                .price(board.getPrice())
                .detail(board.getDetail())
                .writer(board.getUsers().getName())
                .commentContents()
                .build();
    }

    @Data
    public static class CommentDto {
        private String contents;
        private String writer;


    }
}
