package com.talentmarket.KmongJpa.api;

import com.talentmarket.KmongJpa.Dto.ApiResponse;
import com.talentmarket.KmongJpa.Dto.CartRequest;
import com.talentmarket.KmongJpa.Dto.CartResponse;
import com.talentmarket.KmongJpa.config.auth.PrincipalDetails;
import com.talentmarket.KmongJpa.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class CartApi {
    private final CartService cartService;

    @PostMapping("/api/v1/cart")
    public ApiResponse Cart(@RequestBody CartRequest cartRequest, @AuthenticationPrincipal PrincipalDetails principalDetails) {
        cartService.cart(cartRequest,principalDetails);
        return ApiResponse.ok(null);
    }
    @DeleteMapping("/api/v1/cart")
    public ApiResponse deleteCart(@RequestBody List<Long> boardIds, @AuthenticationPrincipal PrincipalDetails principalDetails) {
        cartService.deleteCart(boardIds, principalDetails);
        return ApiResponse.ok(null);
    }
    @GetMapping("/api/v1/cart")
    public ApiResponse getCart(@AuthenticationPrincipal PrincipalDetails principalDetails){
       List<CartResponse> carts = cartService.getCart(principalDetails);

        return ApiResponse.ok(carts);
    }
}
