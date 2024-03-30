package com.talentmarket.KmongJpa.auth;

import com.talentmarket.KmongJpa.global.exception.CustomException;
import com.talentmarket.KmongJpa.global.exception.ErrorCode;
import com.talentmarket.KmongJpa.user.domain.Users;
import com.talentmarket.KmongJpa.user.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Component
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;


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
}
