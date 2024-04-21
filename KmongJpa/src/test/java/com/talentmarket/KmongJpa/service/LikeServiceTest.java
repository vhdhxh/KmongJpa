package com.talentmarket.KmongJpa.service;

import com.talentmarket.KmongJpa.Item.application.dto.WriteRequest;
import com.talentmarket.KmongJpa.Item.application.ItemService;
import com.talentmarket.KmongJpa.Item.domain.Item;
import com.talentmarket.KmongJpa.like.application.dto.LikeResponse;
import com.talentmarket.KmongJpa.like.domain.Like;
import com.talentmarket.KmongJpa.like.application.LikeService;
import com.talentmarket.KmongJpa.user.domain.Users;
import com.talentmarket.KmongJpa.Item.repository.ItemRepository;
import com.talentmarket.KmongJpa.like.repository.LikeRepository;
import com.talentmarket.KmongJpa.user.repository.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
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
    @AfterEach
    void tearDown() {
        userRepository.deleteAll();
    }
@DisplayName("찜 버튼을 두번 누른다.")
@Test
void like버튼두번() {
//given
    Users users = Users.builder().name("name").email("test").password("1234").build();
    userRepository.save(users);
    Item item = Item.builder().users(users).title("제목").contents("내용").build();
    itemRepository.save(item);
//when //then
    likeService.like(1L, users);
    Like like = likeRepository.findById(1L).get();
    assertThat(like.getId()).isEqualTo(1L);
    likeService.like(1L, users);
    Optional<Like> like2 = likeRepository.findById(1L);
    assertThat(like2).isEmpty();
}
    @DisplayName("찜카운터를 갱신한다")
    @Test
    void test() {
    //given
    Long itemId;
    List<String> images = new ArrayList<>();
    images.add("test");
        WriteRequest request = WriteRequest.builder().title("t").contents("t").images(images).build();
       Users user = Users.builder().email("").password("").build();
        userRepository.save(user);
        itemId = itemService.WriteBoard(request , user);
    //when
    likeService.likeCount(itemId,user);
        List<Like> likes = likeRepository.findLikesByItemId(itemId);
    //then
        System.out.println(user.getId());
     assertThat(likes.get(0).getUsers().getId()).isEqualTo(user.getId());
    }

    @DisplayName("찜카운터를 조회한다.")
    @Test
    void test3() {
        //given
        Long itemId;
        List<String> images = new ArrayList<>();
        images.add("test");
        WriteRequest request = WriteRequest.builder().title("t").contents("t").images(images).build();
        Users user = Users.builder().email("").password("").build();
        userRepository.save(user);
        itemId = itemService.WriteBoard(request , user);
        likeService.likeCount(itemId,user);
        List<Like> likes = likeRepository.findLikesByItemId(itemId);
        //when
       LikeResponse likeResponse = likeService.getLike(itemId);
        //then
        assertThat(likeResponse.getLikeCount()).isEqualTo(likes.size());
    }
    @DisplayName("찜 카운터를 해제한다")
    @Test
    void test2() {
    //given
        Long itemId;
        List<String> images = new ArrayList<>();
        images.add("test");
        WriteRequest request = WriteRequest.builder().images(images).title("t").contents("t").build();
        Users user = Users.builder().id(1L).email("").password("").build();
        userRepository.save(user);
        itemId = itemService.WriteBoard(request , user);
    //when
        likeService.likeCount(itemId,user);
    likeService.disLike(itemId ,user);
        List<Like> likes = likeRepository.findLikesByItemId(itemId);
    //then
        assertThat(likes).isEmpty();
    }

}