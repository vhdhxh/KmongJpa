package com.talentmarket.KmongJpa.service;

import com.talentmarket.KmongJpa.Dto.RegisterRequest;
import com.talentmarket.KmongJpa.config.auth.PrincipalDetails;
import com.talentmarket.KmongJpa.entity.Users;
import com.talentmarket.KmongJpa.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.assertj.core.api.ThrowableAssert;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.annotation.Rollback;

import java.util.Optional;
import static org.assertj.core.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
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



    }

    @DisplayName("중복 회원이 가입했다면 예외가 발생한다")
    @Test
    void IfDuplicationMemberHasException() {
    //when

    //given

    //then

    }

    @DisplayName("회원탈퇴를 한다.")
    @Test
    void Withdrawal() {
    //when
        Users users = Users.builder().email("test").password("test").build();
        userRepository.save(users);
    Users user = userRepository.findAllByEmail("test");
    //given
      userRepository.delete(user);
    //then
     assertThat(userRepository.findAllByEmail("test")).isNull();
    }
//    @DisplayName("회원탈퇴시 없는 회원이면 예외를 발생시킨다.")
//    @Test
//    void WithdrawalException() {
//        //when
//
//        PrincipalDetails principalDetails = new PrincipalDetails(userRepository.findAllByEmail("ets"));
//        //given
//        userService.Withdrawal(principalDetails);
//        //then
//        assertThatThrownBy(() -> userService.Withdrawal(principalDetails))
//                .isInstanceOf(IllegalArgumentException.class);


//    }

    }

