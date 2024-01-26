package com.talentmarket.KmongJpa.config.auth;

//시큐리티 설정에서 loginProcecssing url("/login");
// /login 요청이 오면 자동으로 UserDetailsService 타입으로 빈등록이 되어있는 loadUserByUsername 함수가 실행


import com.talentmarket.KmongJpa.Dto.LoginDto;
import com.talentmarket.KmongJpa.entity.Users;
import com.talentmarket.KmongJpa.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PrincipalDetailsService implements UserDetailsService {

    private final UserRepository userRepository;
    //  private UserDTO dto;



    //함수 종료시 @AuthenticationPrincipal 어노테이션이 만들어진다.
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<Users> userEntity = userRepository.findByEmail(email);
        System.out.println(userEntity.isPresent());
        if (userEntity.isPresent()) {
            return new PrincipalDetails(userEntity.get());
        }

        throw new IllegalArgumentException("User not found");

    }
}
