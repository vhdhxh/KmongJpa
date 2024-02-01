package com.talentmarket.KmongJpa.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.talentmarket.KmongJpa.Dto.RegisterRequest;
import com.talentmarket.KmongJpa.config.auth.PrincipalDetailsService;
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
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.request;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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
    @WithMockUser(username = "테스트_최고관리자", roles = {"SUPER"})
    @Test
    void testRegister() throws Exception {
    //when
        RegisterRequest request = RegisterRequest.builder().email("test").password("1234").build();
        mockMvc.perform(MockMvcRequestBuilders
                .post("/api/v1/register").with(csrf())
                .content(objectMapper.writeValueAsString(request))
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(MockMvcResultMatchers.status().isOk());
    //given


        //then

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
                .post("/api/v1/register").with(csrf())
                .content(objectMapper.writeValueAsString(request))
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(MockMvcResultMatchers.status().isBadRequest());
    }
//    @Test
//    public void testLoginSuccess() throws Exception {
//        mockMvc.perform(post("/login")
//                        .param("email", "vhdhxh@naver.com")
//                        .param("password", "1234"))
//                .andExpect(status().isFound());
//
//    }
//
//    @Test
//    public void testLoginFail() throws Exception {
//        mockMvc.perform(post("/login")
//                        .param("email", "vhdhxh@naver.com")
//                        .param("password", "wrong_password"))
//                .andExpect(status().isFound());
//    }
}
