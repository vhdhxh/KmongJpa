package com.talentmarket.KmongJpa.service;

import com.talentmarket.KmongJpa.Dto.CommentWriteDto;
import com.talentmarket.KmongJpa.config.auth.PrincipalDetails;
import com.talentmarket.KmongJpa.entity.Board;
import com.talentmarket.KmongJpa.entity.Comment;
import com.talentmarket.KmongJpa.exception.CustomException;
import com.talentmarket.KmongJpa.exception.ErrorCode;
import com.talentmarket.KmongJpa.repository.BoardRepository;
import com.talentmarket.KmongJpa.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final BoardRepository boardRepository;
    private final CommentRepository commentRepository;

    //댓글 쓰기
    public void CommentWrite (CommentWriteDto commentWriteDto, PrincipalDetails principalDetails) {
       Board board = boardRepository.findById(commentWriteDto.getBoardId()).orElseThrow(()->new CustomException(ErrorCode.BOARD_NOT_FOUND));
       Comment comment = Comment.CreateComment(commentWriteDto , principalDetails , board);
       commentRepository.save(comment);

    }
}
