package com.talentmarket.KmongJpa.Dto;

import com.talentmarket.KmongJpa.entity.Board;
import com.talentmarket.KmongJpa.entity.Comment;
import lombok.AllArgsConstructor;
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
        List<Comment> comments = board.getComment();
        List<CommentDto> map = comments.stream()
                .map(c->new CommentDto(c.getCommentId()
                        ,c.getContents()
                        ,c.getBoard().getComment().get((int)(c.getCommentId()-1)).getUsers().getName()))
                        .collect(Collectors.toList());
//                        )).collect(Collectors.toList());

        return DetailResponse
                .builder()
                .contents(board.getContents())
                .title(board.getTitle())
                .thumbnail(board.getThumbnail())
                .price(board.getPrice())
                .detail(board.getDetail())
                .writer(board.getUsers().getName())
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
