package com.talentmarket.KmongJpa.entity;

import com.talentmarket.KmongJpa.Dto.RegisterRequest;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

@Entity
@Getter
@AllArgsConstructor
@Builder
public class Users {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;
    private String email;
    private String password;
    private String address;
    private String gender;
    private String provider;
    private Long providerId;
    private String image;

protected Users () {}

    public static Users createUsers(RegisterRequest request,String encodePassword) {
    return Users.builder()
            .email(request.getEmail())
            .password(encodePassword)
            .image("Defaultimage.img")
            .address(request.getAddress())
            .gender(request.getGender())
            .build();
    }

}
