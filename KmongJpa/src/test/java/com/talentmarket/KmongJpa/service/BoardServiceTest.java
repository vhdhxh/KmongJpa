package com.talentmarket.KmongJpa.service;

import com.talentmarket.KmongJpa.Dto.WriteRequest;
import com.talentmarket.KmongJpa.entity.Board;
import com.talentmarket.KmongJpa.repository.BoardRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.as;
import static org.assertj.core.api.Assertions.assertThat;import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class BoardServiceTest {
    @Autowired
    BoardRepository boardRepository;
    @Autowired
    BoardService boardService;

    @DisplayName("게시글을 쓴다.")
    @Test
    void writeBoard() {
    //given

        WriteRequest request = WriteRequest.builder().title("제목")
                .price("가격")
                .contents("내용")
                .detail("디테일")
                .writer("작성자")
                .thumbnail("썸네일이미지").build();
    //when
       Long Id = boardService.WriteBoard(request);
   Optional<Board> board = boardRepository.findById(Id);
    //then
    assertThat(Id).isEqualTo(board.get().getId());
    assertThat(board.get().getTitle()).isEqualTo("제목");

    }

}