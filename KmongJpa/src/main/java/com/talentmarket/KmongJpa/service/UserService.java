package com.talentmarket.KmongJpa.service;

import com.talentmarket.KmongJpa.Dto.RegisterRequest;
import com.talentmarket.KmongJpa.config.auth.PrincipalDetails;
import com.talentmarket.KmongJpa.entity.Users;
import com.talentmarket.KmongJpa.repository.UserRepository;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    //회원가입
    public Long Register(RegisterRequest request) {
        if (userRepository.findAllByEmail(request.getEmail())!=null){
            throw new IllegalArgumentException("중복된 회원입니다.");
        }
        String encodePassword = bCryptPasswordEncoder.encode(request.getPassword());
      Users user = Users.createUsers(request,encodePassword);
      Long Id = userRepository.save(user).getId();
      return Id;
    }

     //회원탈퇴
    public void Withdrawal(PrincipalDetails principalDetails){
        if(principalDetails==null){
            throw new IllegalArgumentException("이미 탈퇴된 회원입니다");
        }
        userRepository.delete(principalDetails.getDto());
    }
    }



