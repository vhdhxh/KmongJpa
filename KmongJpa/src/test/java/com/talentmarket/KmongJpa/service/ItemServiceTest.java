package com.talentmarket.KmongJpa.service;

import com.talentmarket.KmongJpa.Item.application.dto.ItemPagingResponse;
import com.talentmarket.KmongJpa.Item.application.dto.DetailResponse;
import com.talentmarket.KmongJpa.Item.application.dto.WriteRequest;
import com.talentmarket.KmongJpa.Item.application.ItemService;
import com.talentmarket.KmongJpa.Item.domain.Item;
import com.talentmarket.KmongJpa.user.domain.Users;
import com.talentmarket.KmongJpa.Item.repository.ItemRepository;
import com.talentmarket.KmongJpa.user.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;

@SpringBootTest
@Transactional
@ActiveProfiles("test")
class ItemServiceTest {
    @Autowired
    ItemRepository itemRepository;
    @Autowired
    ItemService itemService;
    @Autowired
    UserRepository userRepository;

    @DisplayName("게시글을 쓴다.")
    @Test
    void writeBoard() {

        //given
        Users users = Users.builder().email("vhdhxh@naver.com").password("1234").build();

        WriteRequest request = WriteRequest.builder().title("제목")

                .price(100)
                .contents("내용")
                .detail("디테일")

                .thumbnail("썸네일이미지").build();
        //when
//       Long Id = itemService.WriteBoard(request , new PrincipalDetails(users));
//   Optional<Item> board = itemRepository.findById(Id);
//    //then
//    assertThat(Id).isEqualTo(board.get().getId());
//    assertThat(board.get().getTitle()).isEqualTo("제목");


    }

    @DisplayName("작성한 게시글을 수정한다")
    @Test
    void updateBoard() {
        //given
        Users users = Users.builder().email("vhdhxh@naver.com").password("1234").build();
//        PrincipalDetails principalDetails = new PrincipalDetails(users);
        WriteRequest request =
                WriteRequest.builder().title("제목")
                        .price(100)
                        .contents("내용")
                        .detail("디테일")
                        .thumbnail("썸네일이미지").build();
//        Long Id = itemService.WriteBoard(request , principalDetails);

        request.setTitle("변경된 제목");
        request.setPrice(1000);
        request.setContents("변경된 내용");
        request.setDetail("변경된 디테일");
        request.setThumbnail("변경된 썸네일");
        //when
//     Long updatedBoardId = itemService.UpdateBoard(request,Id,principalDetails);
//     Item item = itemRepository.findById(updatedBoardId).get();
        //then
//        assertThat(Id).isEqualTo(updatedBoardId);
//        assertThat(item.getTitle()).isEqualTo("변경된 제목");
//        assertThat(item.getPrice()).isEqualTo(1000);
//        assertThat(item.getContents()).isEqualTo("변경된 내용");
//        assertThat(item.getDetail()).isEqualTo("변경된 디테일");
//        assertThat(item.getThumbnail()).isEqualTo("변경된 썸네일");
//    }
//
//    @DisplayName("게시글을 눌러 상세보기")
//    @Test
//    void test() {
//        //given
//        Users users = Users.builder().email("vhdhxh@naver.com").password("1234").build();
//        WriteRequest request = WriteRequest.builder().title("제목")
//                .price(100)
//                .contents("내용")
//                .detail("디테일")
//                .thumbnail("썸네일이미지").build();
//        //when
//
//        userRepository.save(users);
//        Long Id = itemService.WriteBoard(request , new PrincipalDetails(users));
//        DetailResponse detailResponse = itemService.DetailBoard(Id);
//        //then
//        assertThat(request.getTitle()).isEqualTo(detailResponse.getTitle());
//
//
//    }
//
//    @DisplayName("게시글 페이징을 반환합니다")
//    @Test
//    void test2() {
//        //given
//        Users users = Users.builder().email("vhdhxh@naver.com").password("1234").build();
//        WriteRequest request = WriteRequest.builder().title("제목")
//                .price(100)
//                .contents("내용")
//                .detail("디테일")
//                .thumbnail("썸네일이미지").build();
//
//       Pageable pageable = PageRequest.of(0,6);
//        //when
//
//        userRepository.save(users);
//        Long Id = itemService.WriteBoard(request , new PrincipalDetails(users));
//        Page<ItemPagingResponse> boardList= itemService.DetailBoard(pageable);
//        //then
//        assertThat(boardList.getSize()).isEqualTo(6);
//        assertThat(boardList.getContent().get(0).getPrice()).isEqualTo(request.getPrice());
//
//    }
//
    }
}