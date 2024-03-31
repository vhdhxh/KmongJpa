package com.talentmarket.KmongJpa.auth;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.talentmarket.KmongJpa.global.ApiResponse;
import com.talentmarket.KmongJpa.user.domain.Users;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.net.URISyntaxException;

@RestController
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;
    @PostMapping("/logout2")
    public ApiResponse logout(HttpServletRequest httpServletRequest) {
       authService.logout(httpServletRequest);
        return ApiResponse.ok("logout");
    }

    @PostMapping("/login2")
    public ApiResponse login(HttpServletRequest httpServletRequest, @RequestBody UserDto userDto) {
        authService.login(httpServletRequest,userDto);
        return ApiResponse.ok("login");
    }
    @GetMapping("/test2")
    public ApiResponse test2(@AuthPrincipal Users user) {
        System.out.println();

        return ApiResponse.ok("userDto");
    }

    @GetMapping("/login/oauth2/code/kakao")
    public ApiResponse kakaoLogin(KakaoRequest request) throws JsonProcessingException, URISyntaxException {


        return ApiResponse.ok( authService.kakaoLogin(request));
    }


}
