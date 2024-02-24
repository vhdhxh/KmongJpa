package com.talentmarket.KmongJpa.service;

import com.talentmarket.KmongJpa.Dto.WriteRequest;
import com.talentmarket.KmongJpa.config.auth.PrincipalDetails;
import com.talentmarket.KmongJpa.entity.Like;
import com.talentmarket.KmongJpa.entity.Users;
import com.talentmarket.KmongJpa.repository.LikeRepository;
import com.talentmarket.KmongJpa.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.parameters.P;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@ActiveProfiles("test")
class LikeServiceTest {


    @Autowired
    LikeService likeService;
    @Autowired
    BoardService boardService;
    @Autowired
    LikeRepository likeRepository;
    @Autowired
    UserRepository userRepository;




    @DisplayName("찜카운터를 갱신한다")
    @Test
    void test() {
    //given
    Long boardId;
        WriteRequest request = WriteRequest.builder().title("t").contents("t").build();
       Users user = Users.builder().id(1L).email("").password("").build();
        userRepository.save(user);
   boardId = boardService.WriteBoard(request , new PrincipalDetails(user));

    //when
    likeService.likeCount(boardId,new PrincipalDetails(user));
        List<Like> likes = likeRepository.findLikesByBoardId(boardId);
    //then
     assertThat(likes.get(0).getUsers().getId()).isEqualTo(1L);
    }
    @DisplayName("찜 카운터를 해제한다")
    @Test
    void test2() {
    //given
        Long boardId;
        WriteRequest request = WriteRequest.builder().title("t").contents("t").build();
        Users user = Users.builder().id(1L).email("").password("").build();
        userRepository.save(user);
        boardId = boardService.WriteBoard(request , new PrincipalDetails(user));
    //when
        likeService.likeCount(boardId,new PrincipalDetails(user));
        List<Like> likes = likeRepository.findLikesByBoardId(boardId);
    likeService.disLike(boardId ,new PrincipalDetails(user));
    //then
        assertThat(likes.get(0).getId()).isNull();
    }

}