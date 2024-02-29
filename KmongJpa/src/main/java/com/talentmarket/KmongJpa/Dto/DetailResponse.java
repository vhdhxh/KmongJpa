package com.talentmarket.KmongJpa.Dto;

import com.talentmarket.KmongJpa.entity.Item;
import com.talentmarket.KmongJpa.entity.Comment;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;

@Data
@Builder
public class DetailResponse {
    private String title;
    private String contents;
    private String thumbnail;
    private int price;
    private String detail;
    private String writer;
    private List<CommentDto> commentContents;



    public static DetailResponse ToDto(Item item) {
        List<Comment> comments = item.getComment();
        List<CommentDto> map = comments.stream()
                .map(c->new CommentDto(c.getCommentId()
                        ,c.getContents()
                        ,c.getItem().getComment().get((int)(c.getCommentId()-1)).getUsers().getName()))
                        .collect(Collectors.toList());
//                        )).collect(Collectors.toList());

        return DetailResponse
                .builder()
                .contents(item.getContents())
                .title(item.getTitle())
                .thumbnail(item.getThumbnail())
                .price(item.getPrice())
                .detail(item.getDetail())
                .writer(item.getUsers().getName())
                .commentContents(map)
                .build();
    }

    @Data
    @AllArgsConstructor
    public static class CommentDto {
        private Long CommentId;
        private String contents;
        private String writer;

        public CommentDto(Long commentId, String contents) {
            CommentId = commentId;
            this.contents = contents;
        }
    }
}
