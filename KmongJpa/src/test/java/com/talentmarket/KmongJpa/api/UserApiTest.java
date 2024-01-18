package com.talentmarket.KmongJpa.api;

import com.talentmarket.KmongJpa.config.auth.PrincipalDetailsService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class UserApiTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private PrincipalDetailsService principalDetailsService;
    @Test
    public void testLoginSuccess() throws Exception {
        this.mockMvc.perform(post("/login")
                        .param("email", "vhdhxh@naver.com")
                        .param("password", "1234"))
                .andExpect(status().isFound())
                .andExpect(redirectedUrl("/"));
    }

    @Test
    public void testLoginFail() throws Exception {
        this.mockMvc.perform(post("/login")
                        .param("email", "vhdhxh@naver.com")
                        .param("password", "wrong_password"))
                .andExpect(status().is3xxRedirection());
    }
}
