package com.talentmarket.KmongJpa.cart.repository;

import com.talentmarket.KmongJpa.cart.domain.Cart;
import com.talentmarket.KmongJpa.user.domain.Users;

import java.util.List;

public interface CartRepositoryCustom {
    void deleteCart(List<Long> longs, Users users);

    List<Cart> getCarts(List<Long> longs, Users user);


}
