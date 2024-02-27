package com.talentmarket.KmongJpa.repository;

import com.talentmarket.KmongJpa.config.auth.PrincipalDetails;
import com.talentmarket.KmongJpa.entity.Cart;

import java.util.List;

public interface CartRepositoryCustom {
    void deleteCart(List<Long> longs, PrincipalDetails principalDetails);

    List<Cart> getCarts(List<Long> longs, PrincipalDetails principalDetails);


}
