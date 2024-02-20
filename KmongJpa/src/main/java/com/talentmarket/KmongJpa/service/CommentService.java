package com.talentmarket.KmongJpa.service;

import com.talentmarket.KmongJpa.Dto.CommentWriteDto;
import com.talentmarket.KmongJpa.config.auth.PrincipalDetails;
import com.talentmarket.KmongJpa.entity.Board;
import com.talentmarket.KmongJpa.entity.Comment;
import com.talentmarket.KmongJpa.entity.Users;
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
        Users.checkUserSession(principalDetails);
        Long boardId = commentWriteDto.getBoardId();
       Board board = boardRepository.findById(boardId).orElseThrow(()->new CustomException(ErrorCode.BOARD_NOT_FOUND));
       Comment comment = Comment.CreateComment(commentWriteDto , principalDetails , board);
       commentRepository.save(comment);

    }
    //댓글 수정
    public void commentUpdate(CommentWriteDto commentWriteDto, PrincipalDetails principalDetails, Long commentId) {
        Users.checkUserSession(principalDetails);
        Long boardId = commentWriteDto.getBoardId();
        Board board = boardRepository.findById(boardId).orElseThrow(()->new CustomException(ErrorCode.BOARD_NOT_FOUND));
        Comment comment = commentRepository.findById(commentId).orElseThrow(()->new CustomException(ErrorCode.COMMENT_NOT_FOUND));
        comment.update(commentWriteDto.getContents());

    }

    //댓글 삭제
    public void commentDelete(PrincipalDetails principalDetails,Long commentId) {
        Users.checkUserSession(principalDetails);
        Comment comment = commentRepository.findById(commentId).orElseThrow(()->new CustomException(ErrorCode.COMMENT_NOT_FOUND));
        commentRepository.deleteById(commentId);
    }

//    public void user(PrincipalDetails principalDetails) {
//        if(principalDetails==null){
//            throw new CustomException(ErrorCode.USER_NOT_LOGIN);
//        }
//    }
}
