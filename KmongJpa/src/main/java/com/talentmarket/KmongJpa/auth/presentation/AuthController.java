package com.talentmarket.KmongJpa.auth.presentation;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.talentmarket.KmongJpa.auth.Test4;
import com.talentmarket.KmongJpa.auth.application.dto.KakaoRequest;
import com.talentmarket.KmongJpa.auth.application.OauthProvider;
import com.talentmarket.KmongJpa.auth.application.OauthService;
import com.talentmarket.KmongJpa.auth.UserDto;
import com.talentmarket.KmongJpa.auth.application.AuthService;
import com.talentmarket.KmongJpa.auth.application.dto.LoginResponse;
import com.talentmarket.KmongJpa.auth.util.AuthPrincipal;
import com.talentmarket.KmongJpa.global.ApiResponse;
import com.talentmarket.KmongJpa.global.exception.CustomException;
import com.talentmarket.KmongJpa.global.exception.ErrorCode;
import com.talentmarket.KmongJpa.user.domain.Users;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
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
    private final OauthService oauthService;
    private final OauthProvider oauthProvider;
    @PostMapping("/api/v1/logout")
    public ApiResponse logout(HttpServletRequest httpServletRequest) {
       authService.logout(httpServletRequest);
        return ApiResponse.ok("logout");
    }

    @PostMapping("/api/v1/login")
    public ApiResponse login(HttpServletRequest httpServletRequest, @RequestBody UserDto userDto, HttpServletResponse response) {
        LoginResponse loginResponse = authService.login(httpServletRequest,userDto);
        return ApiResponse.ok(loginResponse);
    }
    @GetMapping("/test2")
    public ApiResponse test2(@AuthPrincipal Users user) {
        System.out.println();

        return ApiResponse.ok("userDto");
    }

    @GetMapping("/login/oauth2/code/kakao")
    public ApiResponse kakaoLogin(KakaoRequest request, HttpServletRequest request2) throws JsonProcessingException, URISyntaxException {



        return ApiResponse.ok( oauthService.kakaoLogin(request,request2));
    }

    @GetMapping("/test3")
    public ApiResponse test3() {
        System.out.println("test3 print");
        return ApiResponse.ok(null);
    }


    @PostMapping("/test4")
    public ApiResponse test4(@RequestBody Test4 test4) {
        System.out.println("test4 print");
        return ApiResponse.ok("testResponse");
    }

    @GetMapping("/test5")
    public ApiResponse test5() {
        throw new CustomException(ErrorCode.USER_NOT_FOUND);

    }


}
