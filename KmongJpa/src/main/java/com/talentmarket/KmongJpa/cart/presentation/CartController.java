package com.talentmarket.KmongJpa.cart.presentation;

import com.talentmarket.KmongJpa.global.ApiResponse;
import com.talentmarket.KmongJpa.cart.application.dto.CartRequest;
import com.talentmarket.KmongJpa.cart.application.dto.CartResponse;
import com.talentmarket.KmongJpa.global.auth.PrincipalDetails;
import com.talentmarket.KmongJpa.cart.application.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class CartController {
    private final CartService cartService;

    @PostMapping("/api/v1/cart")
    public ApiResponse Cart(@RequestBody CartRequest cartRequest, @AuthenticationPrincipal PrincipalDetails principalDetails) {
        cartService.cart(cartRequest,principalDetails);
        return ApiResponse.ok(null);
    }
    @DeleteMapping("/api/v1/cart")
    public ApiResponse deleteCart(@RequestBody List<Long> itemIds, @AuthenticationPrincipal PrincipalDetails principalDetails) {
        cartService.deleteCart(itemIds, principalDetails);
        return ApiResponse.ok(null);
    }
    @GetMapping("/api/v1/cart")
    public ApiResponse getCart(@AuthenticationPrincipal PrincipalDetails principalDetails){
       List<CartResponse> carts = cartService.getCart(principalDetails);

        return ApiResponse.ok(carts);
    }
}
