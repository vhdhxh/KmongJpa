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
class ItemTest {
    @DisplayName("ㅇ")
    @Test
    void test() {
    //given
    Item item = Item.builder().price(100).title("제목").build();
    //when

    //then
    assertThat(item.getTitle()).isEqualTo("제목");
        assertThat(item.getPrice()).isEqualTo(100);

    }

}