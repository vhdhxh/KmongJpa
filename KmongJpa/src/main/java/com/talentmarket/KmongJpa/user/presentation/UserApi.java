package com.talentmarket.KmongJpa.user.presentation;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.talentmarket.KmongJpa.global.ApiResponse;
import com.talentmarket.KmongJpa.sms.application.CheckRequest;
import com.talentmarket.KmongJpa.global.auth.PrincipalDetails;
import com.talentmarket.KmongJpa.user.application.UserService;
import com.talentmarket.KmongJpa.user.application.dto.FindPasswordRequest;
import com.talentmarket.KmongJpa.user.application.dto.RegisterRequest;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

@RestController
@RequiredArgsConstructor
public class UserApi {

    private final UserService userService;

    @PostMapping("/api/v1/user")
    public ResponseEntity Register(@RequestBody @Valid RegisterRequest registerRequest) {
        userService.Register(registerRequest);
        return ResponseEntity.status(HttpStatus.OK).body(null);
    }

    @DeleteMapping("/api/v1/user")
    public ApiResponse Withdrawal(HttpServletRequest request, @AuthenticationPrincipal PrincipalDetails principalDetails) {
        userService.Withdrawal(principalDetails,request);
        return ApiResponse.ok(null);
    }



    @PostMapping("/api/v1/user/sendSms")
    public ApiResponse sendSms(@RequestBody FindPasswordRequest request) throws UnsupportedEncodingException, URISyntaxException, NoSuchAlgorithmException, InvalidKeyException, JsonProcessingException {
        userService.sendSms(request);
        return ApiResponse.ok(null);
    }

    @PostMapping("/api/v1/user/checkSms")
    public ApiResponse checkSms(@RequestBody CheckRequest request)  {
        userService.checkCode(request.getCode());
        return ApiResponse.ok(null);
    }





}
