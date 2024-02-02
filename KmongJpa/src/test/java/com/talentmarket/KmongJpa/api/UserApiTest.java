package com.talentmarket.KmongJpa.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.talentmarket.KmongJpa.Dto.RegisterRequest;
import com.talentmarket.KmongJpa.config.auth.PrincipalDetails;
import com.talentmarket.KmongJpa.config.auth.PrincipalDetailsService;
import com.talentmarket.KmongJpa.entity.Users;
import com.talentmarket.KmongJpa.exception.CustomException;
import com.talentmarket.KmongJpa.exception.ErrorCode;
import com.talentmarket.KmongJpa.service.UserService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.request;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = UserApi.class)
@ActiveProfiles("test")
class UserApiTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private UserService userService;
    @Autowired
    private ObjectMapper objectMapper;

    @DisplayName("회원가입을 한다.")
    @WithMockUser(username = "testUser")
    @Test
    void testRegister() throws Exception {
    //given
        RegisterRequest request = RegisterRequest.builder().email("test@naver.com").password("1234").address("주소").name("닉네임").build();

    //when //then
        mockMvc.perform(MockMvcRequestBuilders
                .post("/api/v1/user").with(csrf())
                .content(objectMapper.writeValueAsString(request))
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(MockMvcResultMatchers.status().isOk());
    }
    @DisplayName("중복된 이메일이면 예외가 터진다.")
    @WithMockUser(username = "테스트_최고관리자", roles = {"SUPER"})
    @Test
    void DuplicateRegister() throws Exception{
    //given
      when(userService.Register(any())).thenThrow(new CustomException(ErrorCode.EMAIL_DUPLICATED));
    //when //then
        RegisterRequest request = RegisterRequest.builder().email("test").password("1234").build();
        mockMvc.perform(MockMvcRequestBuilders
                .post("/api/v1/user").with(csrf())
                .content(objectMapper.writeValueAsString(request))
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @DisplayName("회원가입을 할때 이메일 형식을 지켜야한다.")
    @WithMockUser(username = "테스트_최고관리자", roles = {"SUPER"})
    @Test
    void test() throws Exception {
        //given
        RegisterRequest request = RegisterRequest.builder().email("test").password("1234").build();

        //when

        //then
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/v1/user").with(csrf())
                        .content(objectMapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(jsonPath("$.message").value("이메일 형식을 지켜주세요.")
                );

    }
        @DisplayName("회원가입을 할때 이메일 은 필수로 입력해야한다.")
        @WithMockUser(username = "테스트_최고관리자", roles = {"SUPER"})
        @Test
        void test2() throws Exception {
            //given
            RegisterRequest request = RegisterRequest.builder().password("1234").build();

            //when

            //then
            mockMvc.perform(MockMvcRequestBuilders
                            .post("/api/v1/user").with(csrf())
                            .content(objectMapper.writeValueAsString(request))
                            .contentType(MediaType.APPLICATION_JSON)
                    )
                    .andDo(print())
                    .andExpect(MockMvcResultMatchers.status().isBadRequest())
                    .andExpect(jsonPath("$.message").value("이메일을 입력해주세요.")
                    );



    }

    @DisplayName("회원 탈퇴를 한다")
    @WithMockUser(username = "testUser")
    @Test
    void Withdrawal() throws Exception {
    //given


   //when //then
        mockMvc.perform(MockMvcRequestBuilders
                .delete("/api/v1/user").with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(MockMvcResultMatchers.status().isOk());
    }
    @DisplayName("로그인상태가 아니면 예외가 발생한다.")
    @WithMockUser(username = "testUser")
    @Test
    void WithdrawalException() throws Exception{
    //given
        doThrow(new CustomException(ErrorCode.USER_WITHDRAWLED)).when(userService).Withdrawal(any());

    //when

    //then
        mockMvc.perform(MockMvcRequestBuilders
                .delete("/api/v1/user").with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(MockMvcResultMatchers.status().isBadRequest());

    }
    }






