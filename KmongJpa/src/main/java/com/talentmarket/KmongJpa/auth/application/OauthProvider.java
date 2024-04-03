package com.talentmarket.KmongJpa.auth.application;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.talentmarket.KmongJpa.auth.application.dto.KaKaoUserInfo;
import com.talentmarket.KmongJpa.auth.application.dto.KakaoRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.net.URISyntaxException;



@Service
public class OauthProvider {
    @Value("${KAKAO_RESTAPI_KEY}")
    private String REST_API_KEY;
    @Value("${KAKAO_REDIRECT_URI}")
    private String REDIRECT_URI;
    @Value("${KAKAO_CLIENT_SECRET}")
    private String CLIENT_SECRET;




    public KaKaoUserInfo getUserInfo(String accessToken) throws JsonProcessingException {

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");
        headers.add("Authorization","Bearer "+accessToken);

        HttpEntity<MultiValueMap<String, String>> kakaoTokenRequest =
                new HttpEntity<>(null, headers);
        ResponseEntity<String> response = new RestTemplate().exchange(
                "https://kapi.kakao.com/v2/user/me",
                HttpMethod.GET,
                kakaoTokenRequest,
                String.class);

        ObjectMapper mapper = new ObjectMapper();
        JsonNode json = mapper.readTree(response.getBody());

        String email = json.at("/kakao_account/email").asText();
        String nickname = json.at("/kakao_account/profile/nickname").asText();
        Long id = json.get("id").asLong();
        KaKaoUserInfo kaKaoUserInfo = KaKaoUserInfo.builder().name(nickname).email(email).id(id).build();

        System.out.println(response);



        return kaKaoUserInfo;
    }


    public String getKaKaoToken(KakaoRequest request) throws JsonProcessingException, URISyntaxException {

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


        //response2 로 받은 토큰 조회 및 검증 후 토큰으로 사용자 정보 요청 , 제공받은 사용자 정보로 서비스 회원 여부 확인, 신규사용자면 회원가입 처리 후 세션발급 -> 로그인처리
        // 기존 사용자면 세션발급 -> 로그인처리


        return accessToken;
    }
}
