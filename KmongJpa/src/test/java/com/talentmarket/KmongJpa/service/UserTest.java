package com.talentmarket.KmongJpa.service;

import com.talentmarket.KmongJpa.Dto.RegisterRequest;
import com.talentmarket.KmongJpa.entity.Users;
import com.talentmarket.KmongJpa.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.annotation.Rollback;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
@Rollback(value = false)
class UserTest {
    @Autowired
    UserRepository userRepository;
    @Autowired
    UserService userService;
    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @DisplayName("회원가입을 한다.")
    @Test
    void Register() {
    //when
   RegisterRequest request = RegisterRequest.builder()
            .email("vhdhxh@naver.com")
            .password("1234")
            .address("주소")
            .gender("남자")
            .image("testImage")
            .build();
    //given
  Long Id = userService.Register(request);
  Optional<Users> users = userRepository.findById(Id);
  bCryptPasswordEncoder.matches("1234",users.get().getPassword());

    //then
        assertThat(request.getEmail()).isEqualTo(users.get().getEmail());
        assertThat(request.getAddress()).isEqualTo(users.get().getAddress());
        assertThat(request.getGender()).isEqualTo(users.get().getGender());
        assertThat(bCryptPasswordEncoder.matches("1234",users.get().getPassword())).isTrue();
        assertThat(request.getImage()).isEqualTo(users.get().getImage());


    }

}