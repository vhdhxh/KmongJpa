package com.talentmarket.KmongJpa.service;

import com.talentmarket.KmongJpa.Dto.RegisterRequest;
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
      Users user = IfImageNullhasDefault(request);
      Long Id = userRepository.save(user).getId();
      return Id;
    }

    public Users IfImageNullhasDefault(RegisterRequest request) {
       String encodePassword = bCryptPasswordEncoder.encode(request.getPassword());

        if (request.getImage() == null) {
           return Users.builder()
                    .email(request.getEmail())
                    .address(request.getAddress())
                    .gender(request.getGender())
                    .password(encodePassword)
                    .build();


        }
        return Users.builder()
                .email(request.getEmail())
                .address(request.getAddress())
                .gender(request.getGender())
                .password(encodePassword)
                .image(request.getImage())
                .build();


    }
    }



