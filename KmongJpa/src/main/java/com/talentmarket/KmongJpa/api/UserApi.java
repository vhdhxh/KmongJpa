package com.talentmarket.KmongJpa.api;

import com.talentmarket.KmongJpa.config.auth.PrincipalDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserApi {


    @GetMapping("/")
    public PrincipalDetails Hello(@AuthenticationPrincipal PrincipalDetails principalDetails) {

        return principalDetails;
    }

    @GetMapping("/loginForm")
    String form() {
return "form";
    }
}
