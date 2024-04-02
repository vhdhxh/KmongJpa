package com.talentmarket.KmongJpa.cart.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.talentmarket.KmongJpa.cart.domain.Cart;
import com.talentmarket.KmongJpa.cart.domain.QCart;
import com.talentmarket.KmongJpa.user.domain.Users;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class CartRepositoryCustomImpl implements CartRepositoryCustom {
    private final JPAQueryFactory jpaQueryFactory;
    QCart qCart = QCart.cart;


    @Override
    public void deleteCart(List<Long> longs, Users users) {
        jpaQueryFactory
                .delete(qCart)
//                .where(qCart.item.Id.in(longs),qCart.user.id.eq(principalDetails.getDto().getId()))
                .execute();
    }

    @Override
    public List<Cart> getCarts(List<Long> longs, Users users) {
        return jpaQueryFactory
                .select(qCart)
//                .where(qCart.item.Id.in(longs),qCart.user.id.eq(principalDetails.getDto().getId()))
                .from(qCart)
                .fetch();
    }
}
