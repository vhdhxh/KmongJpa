package com.talentmarket.KmongJpa.user.application.dto;

import com.talentmarket.KmongJpa.user.domain.Users;
import jakarta.validation.constraints.Email;
import lombok.Builder;
import lombok.Getter;

import java.io.Serializable;

@Getter
@Builder
public class LoginDto implements Serializable {

    @Email
    private String email;
    private String password;
    private Users users;

    public static LoginDto createLoginDto(Users user) {
        return LoginDto.builder()
                .email(user.getEmail())
                .password(user.getPassword())
                .users(user)
                .build();
    }
}
