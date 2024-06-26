package com.talentmarket.KmongJpa.Item.application.dto;

import com.talentmarket.KmongJpa.Item.domain.Item;
import com.talentmarket.KmongJpa.Item.domain.ItemImage;
import com.talentmarket.KmongJpa.comment.domain.Comment;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
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
    private List<String> images;



    public static DetailResponse ToDto(Item item, List<ItemImage> images) {
        List<Comment> comments = item.getComment();
        List<CommentDto> map = comments.stream()
                .map(c->new CommentDto(c.getCommentId()
                        ,c.getContents()
                        ,c.getItem().getComment().get((int)(c.getCommentId()-1)).getUsers().getName()))
                        .collect(Collectors.toList());
        List<String> responseImages = images.stream().map(i->i.getImageName()).toList();

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
                .images(responseImages)
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
