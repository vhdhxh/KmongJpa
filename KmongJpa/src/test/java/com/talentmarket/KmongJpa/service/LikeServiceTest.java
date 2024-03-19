package com.talentmarket.KmongJpa.service;

import com.talentmarket.KmongJpa.Dto.WriteRequest;
import com.talentmarket.KmongJpa.config.auth.PrincipalDetails;
import com.talentmarket.KmongJpa.entity.Item;
import com.talentmarket.KmongJpa.entity.Like;
import com.talentmarket.KmongJpa.entity.Users;
import com.talentmarket.KmongJpa.repository.ItemRepository;
import com.talentmarket.KmongJpa.repository.LikeRepository;
import com.talentmarket.KmongJpa.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

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
    @Autowired
    ItemRepository itemRepository;

@DisplayName("찜 버튼을 두번 누른다.")
@Test
void like버튼두번() {
//given
    Users users = Users.builder().name("name").email("test").password("1234").build();
    userRepository.save(users);
    PrincipalDetails principalDetails = new PrincipalDetails(users);
    Item item = Item.builder().users(users).title("제목").contents("내용").build();
    itemRepository.save(item);

//when //then
    likeService.like(1L, principalDetails);
    Like like = likeRepository.findById(1L).get();
    assertThat(like.getId()).isEqualTo(1L);

    likeService.like(1L, principalDetails);
    Optional<Like> like2 = likeRepository.findById(1L);
    assertThat(like2).isEmpty();

}


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
    likeService.likeCount(itemId,user);
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
        likeService.likeCount(itemId,user);
    likeService.disLike(itemId ,user);
        List<Like> likes = likeRepository.findLikesByItemId(itemId);
    //then
        assertThat(likes).isEmpty();
    }

}