package com.talentmarket.KmongJpa.entity;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;


import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
@ActiveProfiles("test")
class BoardTest {
    @DisplayName("")
    @Test
    void test() {
    //given
    Board board = Board.builder().price("100").title("제목").build();
    //when

    //then
    assertThat(board.getTitle()).isEqualTo("제목");
        assertThat(board.getPrice()).isEqualTo("100");

    }

}