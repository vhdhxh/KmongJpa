package com.talentmarket.KmongJpa.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.talentmarket.KmongJpa.Dto.RegisterRequest;
import com.talentmarket.KmongJpa.Dto.WriteRequest;
import com.talentmarket.KmongJpa.service.BoardService;
import com.talentmarket.KmongJpa.service.UserService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;

@WebMvcTest(controllers = BoardApi.class)
@ActiveProfiles("test")
class BoardApiTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private BoardService boardService;
    @Autowired
    private ObjectMapper objectMapper;

    @DisplayName("게시판을 작성한다.")
    @WithMockUser(username = "testUser")
    @Test
    void writeBoard() throws Exception{
    //given
        WriteRequest writeRequest = WriteRequest.builder()
                .title("제목")
                .contents("내용")
                .build();

    //when

    //then
        mockMvc.perform(MockMvcRequestBuilders
                .post("/api/v1/board").with(csrf())
                .content(objectMapper.writeValueAsString(writeRequest))
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(MockMvcResultMatchers.status().isOk());
    }





}