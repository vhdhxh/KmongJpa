package com.talentmarket.KmongJpa.payment.application;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;

@Component
public class ImportClient {
    public boolean cancelPayment(String imp_uid,int price,String imp_key, String imp_secret) throws URISyntaxException, JsonProcessingException {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);


        TokenRequestDto tokenRequest = TokenRequestDto.builder()
                .imp_key(imp_key)
                .imp_secret(imp_secret).build();

        //쌓은 바디를 json형태로 반환
        ObjectMapper objectMapper = new ObjectMapper();
        String body = objectMapper.writeValueAsString(tokenRequest);
        // jsonBody와 헤더 조립
        HttpEntity<String> httpBody = new HttpEntity<>(body, headers);
        RestTemplate restTemplate = new RestTemplate();
        TokenResponseDto response = restTemplate.postForObject(new URI("https://api.iamport.kr/users/getToken"), httpBody, TokenResponseDto.class);
        System.out.println(response.getResponse().getAccess_token());


        String accessToken = response.getResponse().getAccess_token();
        headers.setBearerAuth(accessToken);
        CanselRequest canselRequest = CanselRequest.builder()
                .imp_id(imp_uid)
                .checksum(price)
                .build();
        body = objectMapper.writeValueAsString(canselRequest);

        HttpEntity<String> httpBody2 = new HttpEntity<>(body, headers);

        restTemplate.postForObject(new URI("https://api.iamport.kr/payments/cancel"), httpBody2, TokenResponseDto.class);

        return true;
    }
}
