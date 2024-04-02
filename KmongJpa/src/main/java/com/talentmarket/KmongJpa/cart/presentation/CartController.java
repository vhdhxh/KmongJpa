package com.talentmarket.KmongJpa.cart.presentation;

import com.talentmarket.KmongJpa.auth.UserDto;
import com.talentmarket.KmongJpa.auth.util.AuthPrincipal;
import com.talentmarket.KmongJpa.global.ApiResponse;
import com.talentmarket.KmongJpa.cart.application.dto.CartRequest;
import com.talentmarket.KmongJpa.cart.application.dto.CartResponse;
import com.talentmarket.KmongJpa.cart.application.CartService;
import com.talentmarket.KmongJpa.user.domain.Users;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class CartController {
    private final CartService cartService;

    @PostMapping("/api/v1/cart")
    public ApiResponse Cart(@RequestBody CartRequest cartRequest, @AuthPrincipal Users user) {
        cartService.cart(cartRequest,user);
        return ApiResponse.ok(null);
    }
    @DeleteMapping("/api/v1/cart")
    public ApiResponse deleteCart(@RequestBody List<Long> itemIds,  @AuthPrincipal Users user) {
        cartService.deleteCart(itemIds, user);
        return ApiResponse.ok(null);
    }
    @GetMapping("/api/v1/cart")
    public ApiResponse getCart(@AuthPrincipal Users user){
       List<CartResponse> carts = cartService.getCart(user);

        return ApiResponse.ok(carts);
    }
}
