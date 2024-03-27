package com.talentmarket.KmongJpa.global.auth;

import com.talentmarket.KmongJpa.user.domain.Users;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Collection;
import java.util.Map;

@Data
public class PrincipalDetails implements UserDetails, OAuth2User {

    private Users dto; //콤포지션
    private Map<String,Object> attributes;

    //일반 로그인
    public PrincipalDetails() {}
    public PrincipalDetails(Users dto) {
        this.dto = dto;
        System.out.println(dto.getEmail());

    }
    //oauth 로그인
    public PrincipalDetails(Users dto, Map<String,Object>attributes) {
        this.dto = dto;
        this.attributes = attributes;
    }

    //해당 유저의 권한을 리턴하는 곳
//    @Override
//    public Collection<? extends GrantedAuthority> getAuthorities() {
//        Collection<GrantedAuthority> collect = new ArrayList<>();
//        collect.add(new GrantedAuthority() {
//            @Override
//            public String getAuthority() {
//                return  null;  //dto.getRole;
//            }
//        });
//        return collect;
//    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getPassword() {
        return dto.getPassword();
    }

    @Override
    public String getUsername() {
        return dto.getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {

        // 내 사이트에서 1년동안 회원이 로그인을 안하면 휴면계정이 되게 하고싶을때
        // 현재시간 - 로그인 시간 => 1년을 초과하면 return false
        return true;
    }

    @Override
    public Map<String, Object> getAttributes() {
        return attributes;
    }
    @Override
    public String getName() {
        return null;
    }
}
