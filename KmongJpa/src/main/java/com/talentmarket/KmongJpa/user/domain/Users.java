package com.talentmarket.KmongJpa.user.domain;

import com.talentmarket.KmongJpa.Dto.UserResponse;
import com.talentmarket.KmongJpa.Item.domain.Item;
import com.talentmarket.KmongJpa.global.exception.CustomException;
import com.talentmarket.KmongJpa.global.exception.ErrorCode;
import com.talentmarket.KmongJpa.user.application.dto.RegisterRequest;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@AllArgsConstructor
@Builder
public class Users implements Serializable { //직렬화 클래스 명시



    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;
    private String email;
    private String password;
    private String address;
    private String name;
    private String gender;
    private String phone;
    private String provider;
    private Long providerId;
    private String image;
    @Builder.Default
    @OneToMany(mappedBy = "users")
    private List<Item> items = new ArrayList<>();



protected Users () {}

    public static Users createUsers(RegisterRequest request, String encodePassword) {
    return Users.builder()
            .email(request.getEmail())
            .password(encodePassword)
            .image("Defaultimage.img")
            .address(request.getAddress())
            .name(request.getName())
            .gender(request.getGender())
            .build();
    }
    public static void checkUserSession(Users principalDetails) {
        if (principalDetails == null) {
            throw new CustomException(ErrorCode.USER_NOT_LOGIN);
        }
    }
    public UserResponse getResponse(){
    return UserResponse.builder().email(this.email).image(this.image).name(this.name).build();
    }

}
