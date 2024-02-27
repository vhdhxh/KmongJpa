package com.talentmarket.KmongJpa.service;

import com.talentmarket.KmongJpa.Dto.RegisterRequest;
import com.talentmarket.KmongJpa.config.auth.PrincipalDetails;
import com.talentmarket.KmongJpa.entity.Users;
import com.talentmarket.KmongJpa.exception.CustomException;
import com.talentmarket.KmongJpa.exception.ErrorCode;
import com.talentmarket.KmongJpa.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.antlr.v4.runtime.atn.SemanticContext;
import org.springframework.data.domain.Page;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.session.FindByIndexNameSessionRepository;
import org.springframework.session.Session;
import org.springframework.session.SessionRepository;
import org.springframework.session.jdbc.JdbcIndexedSessionRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static java.util.Optional.empty;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    //회원가입
@Transactional
    public Long Register(RegisterRequest request) {

    userRepository.findByEmail(request.getEmail()).ifPresent(user -> {
        throw new CustomException(ErrorCode.EMAIL_DUPLICATED);
    });

        userRepository.findByEmail(request.getEmail());

        String encodePassword = bCryptPasswordEncoder.encode(request.getPassword());
      Users user = Users.createUsers(request,encodePassword);
      Long Id = userRepository.save(user).getId();
      return Id;
    }

     //회원탈퇴
    @Transactional
    public void Withdrawal(PrincipalDetails principalDetails , HttpServletRequest httpServletRequest){
        Users.checkUserSession(principalDetails);

//        if(userRepository.findById(principalDetails.getDto().getId()).isEmpty()){
//            throw new CustomException(ErrorCode.USER_WITHDRAWLED);
//        };
        userRepository.delete(principalDetails.getDto());
       HttpSession httpSession = httpServletRequest.getSession();
       //세션 저장소에있는 세션데이터를 삭제
       httpSession.invalidate();
    }
    }



