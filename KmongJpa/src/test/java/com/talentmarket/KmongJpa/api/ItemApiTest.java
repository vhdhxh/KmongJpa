package com.talentmarket.KmongJpa.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.talentmarket.KmongJpa.Item.application.dto.WriteRequest;
import com.talentmarket.KmongJpa.Item.presentation.ItemController;
import com.talentmarket.KmongJpa.Item.application.ItemService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@WebMvcTest(controllers = ItemController.class)
@ActiveProfiles("test")
class ItemApiTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private ItemService itemService;
    @Autowired
    private ObjectMapper objectMapper;

    @DisplayName("게시판을 작성한다.")
//    @WithMockUser(username = "testUser")
    @Test
    void writeBoard() throws Exception{
    //given
        WriteRequest writeRequest = WriteRequest.builder()
                .title("제목")
                .contents("내용")
                .build();

    //when

    //then
//        mockMvc.perform(MockMvcRequestBuilders
////                .post("/api/v1/board").with(csrf())
//                .content(objectMapper.writeValueAsString(writeRequest))
//                .contentType(MediaType.APPLICATION_JSON)
//        ).andExpect(MockMvcResultMatchers.status().isOk());


    }

    @DisplayName("게시글을 수정 요청한다.")
//    @WithMockUser(username = "testUser")
    @Test
    void updateBoard() throws Exception{
        //given
        WriteRequest writeRequest = WriteRequest.builder()
                .title("제목")
                .contents("내용")
                .build();

        //when

        //then
//        mockMvc.perform(MockMvcRequestBuilders
//                .post("/api/v1/board/1").with(csrf())
//                .content(objectMapper.writeValueAsString(writeRequest))
//                .contentType(MediaType.APPLICATION_JSON)
//        ).andDo(print()).andExpect(MockMvcResultMatchers.status().isOk());
//
//
    }



}