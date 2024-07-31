package com.talentmarket.KmongJpa.user.presentation;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.talentmarket.KmongJpa.Dto.UserResponse;
import com.talentmarket.KmongJpa.auth.UserDto;
import com.talentmarket.KmongJpa.auth.util.AuthPrincipal;
import com.talentmarket.KmongJpa.global.ApiResponse;
import com.talentmarket.KmongJpa.sms.application.CheckRequest;
import com.talentmarket.KmongJpa.user.application.SimpleMailMessageService;
import com.talentmarket.KmongJpa.user.application.UserService;
import com.talentmarket.KmongJpa.user.application.dto.FindPasswordRequest;
import com.talentmarket.KmongJpa.user.application.dto.RegisterRequest;
import com.talentmarket.KmongJpa.user.domain.Users;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import com.talentmarket.KmongJpa.auth.BCryptPasswordEncoder;
import org.springframework.session.Session;
import org.springframework.session.SessionRepository;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

@RestController
@RequiredArgsConstructor
public class UserApi {

    private final UserService userService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final SessionRepository sessionRepository;
    private final SimpleMailMessageService simpleMailMessageService;
    
    @PostMapping ("/testLogin") 
    public void testLogin (@RequestBody UserDto userDto,HttpServletRequest request) {
        HttpSession httpSession = request.getSession();
        httpSession.setAttribute("userSession" , userDto);

    }
    @GetMapping("/test")
    public void test (@AuthPrincipal Users user, HttpServletRequest httpServletRequest) {
       boolean matches = bCryptPasswordEncoder.matches("1234", user.getPassword());
        System.out.println(matches);

       HttpSession httpSession = httpServletRequest.getSession();
        System.out.println("httpSession:"+httpSession.toString());
        System.out.println("httpSession.getAttributeNames():"+httpSession.getAttributeNames().toString());
        System.out.println("httpSession.getAttribute(\"SPRING_SECURITY_CONTEXT\"):"+httpSession.getAttribute("SPRING_SECURITY_CONTEXT"));

        System.out.println("httpSession.getId():"+httpSession.getId());
        Session session =sessionRepository.findById(httpSession.getId());
        sessionRepository.save(session);

        System.out.println(session);
        System.out.println(session.toString());
        System.out.println(session.getId());



    }

    @GetMapping("/api/v1/user")
    public ApiResponse<UserResponse> getUserInfo(@AuthPrincipal Users user) {
        UserResponse response = user.getResponse();
        return ApiResponse.ok(response);
    }

    @PostMapping("/api/v1/user")
    public ResponseEntity Register(@RequestBody @Valid RegisterRequest registerRequest) {
        userService.Register(registerRequest);
        return ResponseEntity.status(HttpStatus.OK).body(null);
    }

    @DeleteMapping("/api/v1/user")
    public ApiResponse Withdrawal(HttpServletRequest request, @AuthPrincipal Users user) {
        userService.Withdrawal(user,request);
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

    @PostMapping("test/sendMail")
    public ApiResponse testMailSend() {
        simpleMailMessageService.sendEmail("vhdhxh@gmail.com");
        return ApiResponse.ok(null);
    }





}
