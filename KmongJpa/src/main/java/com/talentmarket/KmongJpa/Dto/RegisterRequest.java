package com.talentmarket.KmongJpa.Dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.*;

@Getter
@AllArgsConstructor
@NoArgsConstructor //역직렬화 과정시 objectmapper가 기본생성자 필요
@Builder
@Setter
public class RegisterRequest {

    private String password;
//    @NotNull(message = "주소를 입력해주세요.")
    private String address;
    private String gender;
//    @Email(message = "이메일 형식을 지켜주세요.")
//    @NotNull(message = "이메일을 입력해주세요.")
    private String email;
    private String provider;
//    @NotNull(message = "닉네임을 입력해주세요.")
//    @Pattern(regexp = "^[가-힣]{1,8}$|^[a-zA-Z]{1,16}$", message = "한글은 8자, 영어는 16자로 입력해주세요.")
    private String name;
    private Long providerId;
}
