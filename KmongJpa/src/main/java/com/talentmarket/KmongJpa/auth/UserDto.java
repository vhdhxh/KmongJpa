package com.talentmarket.KmongJpa.auth;


import java.io.Serializable;

public record UserDto(String userEmail , String password , String userName) implements Serializable {
}
