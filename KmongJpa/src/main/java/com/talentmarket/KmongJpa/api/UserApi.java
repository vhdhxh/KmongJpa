package com.talentmarket.KmongJpa.api;

import com.talentmarket.KmongJpa.Dto.ApiResponse;
import com.talentmarket.KmongJpa.Dto.RegisterRequest;
import com.talentmarket.KmongJpa.config.auth.PrincipalDetails;
import com.talentmarket.KmongJpa.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class UserApi {

    private final UserService userService;

    @PostMapping("/api/v1/user")
    public ResponseEntity Register(@RequestBody @Valid RegisterRequest registerRequest) {
        userService.Register(registerRequest);
        return ResponseEntity.status(HttpStatus.OK).body(null);
    }

    @DeleteMapping("/api/v1/user")
    public ApiResponse<Object> Withdrawal(@AuthenticationPrincipal PrincipalDetails principalDetails) {
        userService.Withdrawal(principalDetails);
        return ApiResponse.ok(null);
    }

}
