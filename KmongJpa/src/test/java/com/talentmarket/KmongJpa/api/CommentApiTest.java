//package com.talentmarket.KmongJpa.api;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.talentmarket.KmongJpa.comment.application.dto.CommentWriteDto;
//import com.talentmarket.KmongJpa.comment.presentation.CommentApi;
//import com.talentmarket.KmongJpa.global.exception.CustomException;
//import com.talentmarket.KmongJpa.global.exception.ErrorCode;
//import com.talentmarket.KmongJpa.comment.application.CommentService;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.http.MediaType;
//import org.springframework.test.context.ActiveProfiles;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
//import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
//
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.Mockito.doThrow;
//import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
//
//@WebMvcTest(controllers = CommentApi.class)
//@ActiveProfiles("test")
//class CommentApiTest {
//
//    @Autowired
//    private MockMvc mockMvc;
//    @MockBean
//    private CommentService commentService;
//    @Autowired
//    private ObjectMapper objectMapper;
//
//
//    @DisplayName("댓글 등록 요청을 보냅니다")
//    @WithMockUser(username = "testUser")
//    @Test
//    void CommentWrite() throws Exception{
//    //given
//        CommentWriteDto commentWriteDto = new CommentWriteDto(1L,"contents");
//
//    //when
//
//    //then
//        mockMvc.perform(MockMvcRequestBuilders
//                .post("/api/v1/comment").with(csrf())
//                .content(objectMapper.writeValueAsString(commentWriteDto))
//                .contentType(MediaType.APPLICATION_JSON)
//        ).andExpect(MockMvcResultMatchers.status().isOk());
//    }
//
//    @DisplayName("없는 게시글에 댓글등록 하면 예외가 발생합니다")
//    @WithMockUser(username = "testUser")
//    @Test
//    void WriteCommentException() throws Exception{
//        //given
//        doThrow(new CustomException(ErrorCode.ITEM_NOT_FOUND)).when(commentService).CommentWrite(any(), any());
//        CommentWriteDto commentWriteDto = new CommentWriteDto(1L,"contents");
//
//        //when //then
//        mockMvc.perform(MockMvcRequestBuilders
//                .post("/api/v1/comment").with(csrf())
//                .content(objectMapper.writeValueAsString(commentWriteDto))
//                .contentType(MediaType.APPLICATION_JSON)
//
//        ).andExpect(MockMvcResultMatchers.status().isBadRequest())
//                .andExpect(jsonPath("$.message").value("게시글을 찾을 수 없습니다."))
//                .andDo(print());
//    }
//
//}