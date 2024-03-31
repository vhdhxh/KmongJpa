package com.talentmarket.KmongJpa.auth;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.talentmarket.KmongJpa.global.exception.CustomException;
import com.talentmarket.KmongJpa.global.exception.ErrorCode;
import com.talentmarket.KmongJpa.payment.application.TokenResponseDto;
import com.talentmarket.KmongJpa.user.domain.Users;
import com.talentmarket.KmongJpa.user.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.*;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;

@Component
@RequiredArgsConstructor
@PropertySource("classpath:config.properties")
public class AuthService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    @Value("${KAKAO_RESTAPI_KEY}")
    private static String REST_API_KEY;
    @Value("${KAKAO_REDIRECT_URI}")
    private static String REDIRECT_URI;
    @Value("${KAKAO_CLIENT_SECRET}")
    private static String CLIENT_SECRET;


    //로그인 -> 비밀번호 체크 -> 맞으면 세션 생성
    public void login(HttpServletRequest httpServletRequest , UserDto userDto) {
        HttpSession httpSession = httpServletRequest.getSession();
        if(httpSession.getAttribute("user")!=null){
            httpSession.setMaxInactiveInterval(1800);
            return;
        }
        Users user = userRepository.findByEmail(userDto.userEmail()).orElseThrow(()->new CustomException(ErrorCode.USER_NOT_FOUND));

        boolean passwordCheck = passwordEncoder.matches(userDto.password(), user.getPassword());
        if(!passwordCheck){
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다");
        }

        httpSession.setAttribute("user" , userDto);
        httpSession.setMaxInactiveInterval(1800);


    }

    public void logout(HttpServletRequest httpServletRequest) {
       HttpSession httpSession =  httpServletRequest.getSession();
       httpSession.invalidate();

    }
    public void kakao() {


    }

    public ResponseEntity<String> kakaoLogin(KakaoRequest request) throws JsonProcessingException, URISyntaxException {
        System.out.println(request);
       String code = request.getCode();

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");


        KaKaoResponse response = KaKaoResponse.builder()
                .client_id(REST_API_KEY)
                .client_secret(CLIENT_SECRET)
                .redirect_uri(REDIRECT_URI)
                .grant_type("authorization_code")
                .code(code)
                .build();

        //쌓은 바디를 json형태로 반환
        ObjectMapper objectMapper = new ObjectMapper();
        String body = objectMapper.writeValueAsString(response);
        // jsonBody와 헤더 조립
        HttpEntity<String> httpBody = new HttpEntity<>(body, headers);
        RestTemplate restTemplate = new RestTemplate();


        // HTTP Body 생성
        MultiValueMap<String, String> body2 = new LinkedMultiValueMap<>();
        body2.add("grant_type", "authorization_code");
        body2.add("client_id", REST_API_KEY);
        body2.add("client_secret", CLIENT_SECRET);
        body2.add("redirect_uri", REDIRECT_URI);
        body2.add("code", code);

        HttpEntity<MultiValueMap<String, String>> kakaoTokenRequest =
                new HttpEntity<>(body2, headers);
        ResponseEntity<String> response2 = new RestTemplate().exchange(
                "https://kauth.kakao.com/oauth/token",
                HttpMethod.POST,
                kakaoTokenRequest,
                String.class);
        System.out.println(response2);

        //response2 로 받은 토큰 조회 및 검증 후 토큰으로 사용자 정보 요청 , 제공받은 사용자 정보로 서비스 회원 여부 확인, 신규사용자면 회원가입 처리 후 세션발급 -> 로그인처리
        // 기존 사용자면 세션발급 -> 로그인처리

//        KakaoTokenDto res = restTemplate.postForObject(new URI("https://kauth.kakao.com/oauth/token"), httpBody, KakaoTokenDto.class);

      return response2;
    }
}
