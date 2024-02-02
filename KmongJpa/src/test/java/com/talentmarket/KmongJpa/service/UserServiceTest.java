package com.talentmarket.KmongJpa.service;

import com.talentmarket.KmongJpa.Dto.RegisterRequest;
import com.talentmarket.KmongJpa.config.auth.PrincipalDetails;
import com.talentmarket.KmongJpa.entity.Users;
import com.talentmarket.KmongJpa.exception.CustomException;
import com.talentmarket.KmongJpa.repository.UserRepository;
import jakarta.transaction.Transactional;
import jakarta.xml.bind.ValidationException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;
import static org.assertj.core.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
class UserServiceTest {
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
           .name("닉네임")
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
    @DisplayName("회원가입 유효성 검사에 실패한다.")
    @Test
    void test() {
    //given
RegisterRequest request = RegisterRequest.builder().email("test").password("1234").build();
    //when

    //then
        assertThatThrownBy(()->userService.Register(request))
                .isInstanceOf(ValidationException.class);
    }

    @DisplayName("중복 회원이 가입했다면 예외가 발생한다")
    @Test
    void IfDuplicationMemberHasException() {
    //given
        RegisterRequest request = RegisterRequest.builder()
                .email("vhdhxh@naver.com")
                .password("1234")
                .address("주소")
                .gender("남자")
                .name("닉네임")
                .build();
        RegisterRequest request2 = RegisterRequest.builder()
                .email("vhdhxh@naver.com")
                .password("1234")
                .address("주소")
                .gender("남자")
                .name("닉네임")
                .build();
        userService.Register(request);


      //when  //then
     assertThatThrownBy(()->userService.Register(request2))
             .isInstanceOf(CustomException.class)
             .hasMessage("중복된 이메일입니다.");
    }

    @DisplayName("회원탈퇴를 한다.")
    @Test
    void Withdrawal() {
    //when
        Users users = Users.builder().email("test").password("test").build();
        userRepository.save(users);
    Optional<Users> user = userRepository.findByEmail("test");
        PrincipalDetails principalDetails = new PrincipalDetails(user.get());
    //given
//      userRepository.delete(user.get());
        userService.Withdrawal(principalDetails);
    //then
     assertThat(userRepository.findByEmail("test")).isEmpty();
    }
//    @DisplayName("회원탈퇴시 없는 회원이면 예외를 발생시킨다.")
//    @Test
//    void WithdrawalException() {
//        //when
//        Users user = Users.builder().email("test").build();
//        userRepository.save(user);
//
//        PrincipalDetails principalDetails = new PrincipalDetails(userRepository.findAllByEmail("test"));
//        //given
//        userService.Withdrawal(principalDetails);
//        PrincipalDetails principalDetails2 = new PrincipalDetails(userRepository.findAllByEmail("test"));
//
//        //then
//        assertThatThrownBy(() -> userService.Withdrawal(principalDetails2))
//                .isInstanceOf(IllegalArgumentException.class)
//                .hasMessage("이미 탈퇴된 회원입니다");
//
//
//    }

    }

