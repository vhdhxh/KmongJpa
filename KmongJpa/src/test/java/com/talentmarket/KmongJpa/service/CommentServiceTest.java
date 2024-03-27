package com.talentmarket.KmongJpa.service;

import com.talentmarket.KmongJpa.comment.application.dto.CommentWriteDto;
import com.talentmarket.KmongJpa.Item.application.dto.WriteRequest;
import com.talentmarket.KmongJpa.comment.application.CommentService;
import com.talentmarket.KmongJpa.global.auth.PrincipalDetails;
import com.talentmarket.KmongJpa.Item.domain.Item;
import com.talentmarket.KmongJpa.comment.domain.Comment;
import com.talentmarket.KmongJpa.user.domain.Users;
import com.talentmarket.KmongJpa.global.exception.CustomException;
import com.talentmarket.KmongJpa.Item.repository.ItemRepository;
import com.talentmarket.KmongJpa.comment.repository.CommentRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;




@SpringBootTest
@Transactional
@ActiveProfiles("test")
class CommentServiceTest {
    @Autowired
    CommentService commentService;
    @Autowired
    CommentRepository commentRepository;
    @Autowired
    ItemRepository itemRepository;

    @DisplayName("게시글에 댓글을 등록합니다")
    @Test
    void WriteComment() {
    //given

    CommentWriteDto commentWriteDto = new CommentWriteDto(1L,"test");
        WriteRequest writeRequest = WriteRequest.builder().title("test").contents("test").build();
     Item item = Item.createBoard(writeRequest,new PrincipalDetails(Users.builder().name("").build()));
     itemRepository.save(item);
    //when
        commentService.CommentWrite(commentWriteDto ,new PrincipalDetails());
        Comment comment = commentRepository.findById(1L).get();
    //then

        assertThat(comment.getContents()).isEqualTo("test");
    }

    @DisplayName("없는 게시글에 댓글을 등록하면 예외를 발생시킨다.")
    @Test
    void WriteCommentNotFoundBoardException() {
        //given

        CommentWriteDto commentWriteDto = new CommentWriteDto(1L,"test");

        //when //then
        assertThatThrownBy(()-> commentService.CommentWrite(commentWriteDto ,new PrincipalDetails()))
                .isInstanceOf(CustomException.class)
                .hasMessage("상품을 찾을 수 없습니다.")
                ;

    }

}