package com.talentmarket.KmongJpa.api;

import com.talentmarket.KmongJpa.config.auth.PrincipalDetailsService;
import com.talentmarket.KmongJpa.service.UserService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.request;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class UserApiTest {

    @Autowired
    private MockMvc mockMvc;
    @Mock
    private UserService userService;

    @DisplayName("회원가입을 한다.")
    @Test
    void testRegister() throws Exception {
    //when

    //given
        mockMvc.perform(post("/api/v1/register").param("email","test")
                .param("password","1234")).andExpect(status().isOk());

        //then

    }
    @Test
    public void testLoginSuccess() throws Exception {
        this.mockMvc.perform(post("/login")
                        .param("email", "vhdhxh@naver.com")
                        .param("password", "1234"))
                .andExpect(status().isFound());

    }

    @Test
    public void testLoginFail() throws Exception {
        this.mockMvc.perform(post("/login")
                        .param("email", "vhdhxh@naver.com")
                        .param("password", "wrong_password"))
                .andExpect(status().isFound());
    }
}
