package com.talentmarket.KmongJpa.Dto;

import com.talentmarket.KmongJpa.entity.Board;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Data
public class CommentWriteDto {
    private Long boardId;
    private String contents;
}
