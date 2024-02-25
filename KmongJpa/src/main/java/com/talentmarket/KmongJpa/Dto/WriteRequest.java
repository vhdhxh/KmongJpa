package com.talentmarket.KmongJpa.Dto;

import com.talentmarket.KmongJpa.entity.Board;
import com.talentmarket.KmongJpa.entity.Users;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToOne;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WriteRequest {
    private String title;
    private String contents;
    private String thumbnail;
    private String price;
    private String detail;

    public static WriteRequest ToDto(Board board) {
        return WriteRequest
                .builder()
                .contents(board.getContents())
                .title(board.getTitle())
                .thumbnail(board.getThumbnail())
                .price(board.getPrice())
                .detail(board.getDetail())
                .build();
    }
}
