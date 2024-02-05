package com.talentmarket.KmongJpa.entity;

import com.talentmarket.KmongJpa.Dto.RegisterRequest;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

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
    private String provider;
    private Long providerId;
    private String image;
    @Builder.Default
    @OneToMany(mappedBy = "user",cascade = CascadeType.ALL,orphanRemoval = true)
    private List<Board> boards = new ArrayList<>();



protected Users () {}

    public static Users createUsers(RegisterRequest request,String encodePassword) {
    return Users.builder()
            .email(request.getEmail())
            .password(encodePassword)
            .image("Defaultimage.img")
            .address(request.getAddress())
            .name(request.getName())
            .gender(request.getGender())
            .build();
    }

}
