package com.talentmarket.KmongJpa.service;

import com.talentmarket.KmongJpa.Dto.BoardPagingResponse;
import com.talentmarket.KmongJpa.Dto.DetailResponse;
import com.talentmarket.KmongJpa.Dto.RegisterRequest;
import com.talentmarket.KmongJpa.Dto.WriteRequest;
import com.talentmarket.KmongJpa.config.auth.PrincipalDetails;
import com.talentmarket.KmongJpa.entity.Board;
import com.talentmarket.KmongJpa.entity.Users;
import com.talentmarket.KmongJpa.repository.BoardRepository;
import com.talentmarket.KmongJpa.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.as;
import static org.assertj.core.api.Assertions.assertThat;import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@ActiveProfiles("test")
class BoardServiceTest {
    @Autowired
    BoardRepository boardRepository;
    @Autowired
    BoardService boardService;
    @Autowired
    UserRepository userRepository;

    @DisplayName("게시글을 쓴다.")
    @Test
    void writeBoard() {
    //given
        Users users = Users.builder().email("vhdhxh@naver.com").password("1234").build();

        WriteRequest request = WriteRequest.builder().title("제목")

                .price("가격")
                .contents("내용")
                .detail("디테일")

                .thumbnail("썸네일이미지").build();
    //when
       Long Id = boardService.WriteBoard(request , new PrincipalDetails(users));
   Optional<Board> board = boardRepository.findById(Id);
    //then
    assertThat(Id).isEqualTo(board.get().getId());
    assertThat(board.get().getTitle()).isEqualTo("제목");




    }
    @DisplayName("작성한 게시글을 수정한다")
    @Test
    void updateBoard() {
    //given
        Users users = Users.builder().email("vhdhxh@naver.com").password("1234").build();

        WriteRequest request =
                WriteRequest.builder().title("제목")
                .price("가격")
                .contents("내용")
                .detail("디테일")
                .thumbnail("썸네일이미지").build();
        Long Id = boardService.WriteBoard(request , new PrincipalDetails(users));

        request.setTitle("변경된 제목");
        request.setPrice("변경된 가격");
        request.setContents("변경된 내용");
        request.setDetail("변경된 디테일");
        request.setThumbnail("변경된 썸네일");
    //when
     Long updatedBoardId = boardService.UpdateBoard(request,Id);
     Board board = boardRepository.findById(updatedBoardId).get();
    //then
        assertThat(Id).isEqualTo(updatedBoardId);
        assertThat(board.getTitle()).isEqualTo("변경된 제목");
        assertThat(board.getPrice()).isEqualTo("변경된 가격");
        assertThat(board.getContents()).isEqualTo("변경된 내용");
        assertThat(board.getDetail()).isEqualTo("변경된 디테일");
        assertThat(board.getThumbnail()).isEqualTo("변경된 썸네일");
    }

    @DisplayName("게시글을 눌러 상세보기")
    @Test
    void test() {
        //given
        Users users = Users.builder().email("vhdhxh@naver.com").password("1234").build();
        WriteRequest request = WriteRequest.builder().title("제목")
                .price("가격")
                .contents("내용")
                .detail("디테일")
                .thumbnail("썸네일이미지").build();
        //when

        userRepository.save(users);
        Long Id = boardService.WriteBoard(request , new PrincipalDetails(users));
        DetailResponse detailResponse = boardService.DetailBoard(Id);
        //then
        assertThat(request.getTitle()).isEqualTo(detailResponse.getTitle());


    }

    @DisplayName("게시글 페이징을 반환합니다")
    @Test
    void test2() {
        //given
        Users users = Users.builder().email("vhdhxh@naver.com").password("1234").build();
        WriteRequest request = WriteRequest.builder().title("제목")
                .price("가격")
                .contents("내용")
                .detail("디테일")
                .thumbnail("썸네일이미지").build();

       Pageable pageable = PageRequest.of(0,6);
        //when

        userRepository.save(users);
        Long Id = boardService.WriteBoard(request , new PrincipalDetails(users));
        Page<BoardPagingResponse> boardList= boardService.DetailBoard(pageable);
        //then
        assertThat(boardList.getSize()).isEqualTo(6);
        assertThat(boardList.getContent().get(0).getPrice()).isEqualTo(request.getPrice());

    }

}