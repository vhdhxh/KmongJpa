package com.talentmarket.KmongJpa.api;

import com.talentmarket.KmongJpa.Dto.RegisterRequest;
import com.talentmarket.KmongJpa.config.auth.PrincipalDetails;
import com.talentmarket.KmongJpa.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserApi {

    private final UserService userService;

    @PostMapping("/api/v1/register")
    public void Register(@RequestBody @Valid RegisterRequest registerRequest) {
        userService.Register(registerRequest);
    }

}
