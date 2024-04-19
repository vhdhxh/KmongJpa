package com.talentmarket.KmongJpa.cart.domain;

import com.talentmarket.KmongJpa.cart.application.dto.CartResponse;

import java.util.List;
import java.util.stream.Collectors;

public class CartItems {
    public static final int MAX_CART_ITEM_SIZE = 50;
    List<CartItem> cartItems;

    public CartItems(List<CartItem> cartItems) {
        validateSize(cartItems);
        this.cartItems = cartItems;

    }

    public void validateSize(List<CartItem> cartItems) {
        if(cartItems.size() > MAX_CART_ITEM_SIZE)
            throw new IllegalStateException("장바구니의 상품은 최대 " + MAX_CART_ITEM_SIZE + "까지만 추가할수 있습니다.");
    }

    public List<CartResponse> toResponse() {
        return cartItems.stream()
                .map(c -> new CartResponse(c.getItem().getTitle(), c.getCount(), c.getItem().getPrice())).collect(Collectors.toList());

    }



}
