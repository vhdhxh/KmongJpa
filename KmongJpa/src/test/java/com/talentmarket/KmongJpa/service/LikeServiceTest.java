package com.talentmarket.KmongJpa.service;

import com.talentmarket.KmongJpa.Dto.WriteRequest;
import com.talentmarket.KmongJpa.config.auth.PrincipalDetails;
import com.talentmarket.KmongJpa.entity.Like;
import com.talentmarket.KmongJpa.entity.Users;
import com.talentmarket.KmongJpa.repository.LikeRepository;
import com.talentmarket.KmongJpa.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
@ActiveProfiles("test")
class LikeServiceTest {


    @Autowired
    LikeService likeService;
    @Autowired
    ItemService itemService;
    @Autowired
    LikeRepository likeRepository;
    @Autowired
    UserRepository userRepository;




    @DisplayName("찜카운터를 갱신한다")
    @Test
    void test() {
    //given
    Long itemId;
        WriteRequest request = WriteRequest.builder().title("t").contents("t").build();
       Users user = Users.builder().id(1L).email("").password("").build();
        userRepository.save(user);
        itemId = itemService.WriteBoard(request , new PrincipalDetails(user));

    //when
    likeService.likeCount(itemId,new PrincipalDetails(user));
        List<Like> likes = likeRepository.findLikesByItemId(itemId);
    //then
     assertThat(likes.get(0).getUsers().getId()).isEqualTo(1L);
    }
    @DisplayName("찜 카운터를 해제한다")
    @Test
    void test2() {
    //given
        Long itemId;
        WriteRequest request = WriteRequest.builder().title("t").contents("t").build();
        Users user = Users.builder().id(1L).email("").password("").build();
        userRepository.save(user);
        PrincipalDetails principalDetails = new PrincipalDetails(user);
        itemId = itemService.WriteBoard(request , principalDetails);
    //when
        likeService.likeCount(itemId,principalDetails);
    likeService.disLike(itemId ,principalDetails);
        List<Like> likes = likeRepository.findLikesByItemId(itemId);
    //then
        assertThat(likes).isEmpty();
    }

}