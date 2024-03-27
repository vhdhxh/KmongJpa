package com.talentmarket.KmongJpa.cart.repository;

import com.talentmarket.KmongJpa.global.auth.PrincipalDetails;
import com.talentmarket.KmongJpa.cart.domain.Cart;

import java.util.List;

public interface CartRepositoryCustom {
    void deleteCart(List<Long> longs, PrincipalDetails principalDetails);

    List<Cart> getCarts(List<Long> longs, PrincipalDetails principalDetails);


}
