package com.talentmarket.KmongJpa.auth.application;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.talentmarket.KmongJpa.auth.application.dto.LoginResponse;
import com.talentmarket.KmongJpa.auth.application.dto.KakaoRequest;
import com.talentmarket.KmongJpa.auth.UserDto;
import com.talentmarket.KmongJpa.global.exception.CustomException;
import com.talentmarket.KmongJpa.global.exception.ErrorCode;
import com.talentmarket.KmongJpa.user.domain.Users;
import com.talentmarket.KmongJpa.user.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.*;
import com.talentmarket.KmongJpa.auth.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.net.URISyntaxException;

@Component
@RequiredArgsConstructor
@PropertySource("classpath:config.properties")
public class AuthService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;


    @Value("${KAKAO_RESTAPI_KEY}")
    private String REST_API_KEY;
    @Value("${KAKAO_REDIRECT_URI}")
    private String REDIRECT_URI;
    @Value("${KAKAO_CLIENT_SECRET}")
    private String CLIENT_SECRET;


    //로그인 -> 비밀번호 체크 -> 맞으면 세션 생성
    public LoginResponse login(HttpServletRequest httpServletRequest, UserDto userDto) {
        HttpSession httpSession = httpServletRequest.getSession(false);
        if (httpSession != null) {
            System.out.println("httpsessionsdfdsf = "+httpSession);
            httpSession.setMaxInactiveInterval(1801);
            return LoginResponse.builder().build();
        }
        Users user = userRepository.findByEmail(userDto.userEmail()).orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

        boolean passwordCheck = passwordEncoder.matches(userDto.password(), user.getPassword());
        if (!passwordCheck) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다");
        }
        httpSession = httpServletRequest.getSession(true);
        httpSession.setAttribute("user", userDto);
        httpSession.setMaxInactiveInterval(1801);
        LoginResponse loginResponse = LoginResponse.builder().userEmail(user.getEmail()).userImage(user.getImage()).userName(user.getName()).build();
      return loginResponse;
    }

    public void logout(HttpServletRequest httpServletRequest) {
        HttpSession httpSession = httpServletRequest.getSession();
        httpSession.invalidate();

    }

    public void kakao() {


    }

    public ResponseEntity<String> kakaoLogin(KakaoRequest request) throws JsonProcessingException, URISyntaxException {

        String code = request.getCode();

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

        // HTTP Body 생성
        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("grant_type", "authorization_code");
        body.add("client_id", REST_API_KEY);
        body.add("client_secret", CLIENT_SECRET);
        body.add("redirect_uri", REDIRECT_URI);
        body.add("code", code);

        HttpEntity<MultiValueMap<String, String>> kakaoTokenRequest =
                new HttpEntity<>(body, headers);
        ResponseEntity<String> response = new RestTemplate().exchange(
                "https://kauth.kakao.com/oauth/token",
                HttpMethod.POST,
                kakaoTokenRequest,
                String.class);

        ObjectMapper mapper = new ObjectMapper();
        JsonNode json = mapper.readTree(response.getBody());
        String accessToken = json.get("access_token").asText();
        System.out.println("----");
        System.out.println("Access Token: " + accessToken);
        System.out.println("----");

        System.out.println(response.getBody());

        //response2 로 받은 토큰 조회 및 검증 후 토큰으로 사용자 정보 요청 , 제공받은 사용자 정보로 서비스 회원 여부 확인, 신규사용자면 회원가입 처리 후 세션발급 -> 로그인처리
        // 기존 사용자면 세션발급 -> 로그인처리


        return response;
    }
}
