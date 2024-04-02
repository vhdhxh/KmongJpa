package com.talentmarket.KmongJpa.comment.application;

import com.talentmarket.KmongJpa.comment.application.dto.CommentWriteDto;
import com.talentmarket.KmongJpa.comment.repository.CommentRepository;
import com.talentmarket.KmongJpa.Item.domain.Item;
import com.talentmarket.KmongJpa.comment.domain.Comment;
import com.talentmarket.KmongJpa.user.domain.Users;
import com.talentmarket.KmongJpa.global.exception.CustomException;
import com.talentmarket.KmongJpa.global.exception.ErrorCode;
import com.talentmarket.KmongJpa.Item.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class CommentService {

    private final ItemRepository itemRepository;
    private final CommentRepository commentRepository;

    //댓글 쓰기
    public void CommentWrite (CommentWriteDto commentWriteDto, Users principalDetails) {
        Users.checkUserSession(principalDetails);
        Long boardId = commentWriteDto.getBoardId();
       Item item = itemRepository.findById(boardId).orElseThrow(()->new CustomException(ErrorCode.ITEM_NOT_FOUND));
       Comment comment = Comment.CreateComment(commentWriteDto , principalDetails , item);
       commentRepository.save(comment);

    }
    //댓글 수정
    public void commentUpdate(CommentWriteDto commentWriteDto, Users principalDetails, Long commentId) {
        Users.checkUserSession(principalDetails);
        Long boardId = commentWriteDto.getBoardId();
        Item item = itemRepository.findById(boardId).orElseThrow(()->new CustomException(ErrorCode.ITEM_NOT_FOUND));
        Comment comment = commentRepository.findById(commentId).orElseThrow(()->new CustomException(ErrorCode.COMMENT_NOT_FOUND));
        comment.update(commentWriteDto.getContents());

    }

    //댓글 삭제
    public void commentDelete(Users principalDetails, Long commentId) {
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
