package com.talentmarket.KmongJpa.auth.application;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.talentmarket.KmongJpa.auth.application.OauthProvider;
import com.talentmarket.KmongJpa.auth.application.dto.KaKaoUserInfo;
import com.talentmarket.KmongJpa.auth.application.dto.KakaoRequest;
import com.talentmarket.KmongJpa.user.domain.Users;
import com.talentmarket.KmongJpa.user.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import com.talentmarket.KmongJpa.auth.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.net.URISyntaxException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OauthService {
    private final OauthProvider oauthProvider;
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public KaKaoUserInfo kakaoLogin(KakaoRequest kakaoRequest , HttpServletRequest request) throws URISyntaxException, JsonProcessingException {

        String accessToken = oauthProvider.getKaKaoToken(kakaoRequest);
        KaKaoUserInfo kaKaoUserInfo =  oauthProvider.getUserInfo(accessToken);

        HttpSession httpSession = request.getSession();
        if(httpSession.getAttribute("user")!=null) {
           KaKaoUserInfo kaKaoUserSessionInfo = (KaKaoUserInfo) httpSession.getAttribute("user");
            httpSession.setMaxInactiveInterval(1800);
            return kaKaoUserSessionInfo;

        }

//        Optional<Users> optionalUser = userRepository.findByProviderAndProviderId("kakao",kaKaoUserInfo.getId());
        boolean registerCheck = userRepository.existsByProviderAndProviderId("kakao", kaKaoUserInfo.getId());
//        if(!optionalUser.isPresent()) {
//            kakaoRegister(kaKaoUserInfo);
//        }
        if(!registerCheck) {
            kakaoRegister(kaKaoUserInfo);
        }


        httpSession.setAttribute("user",kaKaoUserInfo);

        return kaKaoUserInfo;

    }

    public void kakaoRegister(KaKaoUserInfo kaKaoUserInfo) {
       String pw =  passwordEncoder.encode(String.valueOf(kaKaoUserInfo.getId()));
        Users user = Users.builder().email(kaKaoUserInfo.getEmail()+"/kakao").name(kaKaoUserInfo.getName()).password(pw).provider("kakao")
                .providerId(kaKaoUserInfo.getId()).build();
     userRepository.save(user);
    }
}
