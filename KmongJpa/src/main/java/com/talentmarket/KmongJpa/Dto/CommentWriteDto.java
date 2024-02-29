package com.talentmarket.KmongJpa.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CommentWriteDto {
    private Long boardId;
    private String contents;
}
