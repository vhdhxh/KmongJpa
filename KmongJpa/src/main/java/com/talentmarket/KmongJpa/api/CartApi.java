package com.talentmarket.KmongJpa.api;

import com.talentmarket.KmongJpa.Dto.ApiResponse;
import com.talentmarket.KmongJpa.Dto.CartDto;
import com.talentmarket.KmongJpa.config.auth.PrincipalDetails;
import com.talentmarket.KmongJpa.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class CartApi {
    private final CartService cartService;
    @PostMapping("/api/v1/cart")
    public ApiResponse Cart(@RequestBody CartDto cartDto, @AuthenticationPrincipal PrincipalDetails principalDetails) {
        cartService.cart(cartDto,principalDetails);
        return ApiResponse.ok(null);
    }
    @DeleteMapping("/api/v1/cart")
    public ApiResponse deleteCart(@RequestBody List<Long> boardIds, @AuthenticationPrincipal PrincipalDetails principalDetails) {
        cartService.deleteCart(boardIds, principalDetails);
        return ApiResponse.ok(null);
    }
}
