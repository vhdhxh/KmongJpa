package com.talentmarket.KmongJpa.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.talentmarket.KmongJpa.config.auth.PrincipalDetails;
import com.talentmarket.KmongJpa.entity.Cart;
import com.talentmarket.KmongJpa.entity.QBoard;
import com.talentmarket.KmongJpa.entity.QCart;
import com.talentmarket.KmongJpa.entity.QUsers;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@RequiredArgsConstructor
public class CartRepositoryCustomImpl implements CartRepositoryCustom {
    private final JPAQueryFactory jpaQueryFactory;
    QCart qCart = QCart.cart;
    QBoard qBoard = QBoard.board;
    QUsers qUsers = QUsers.users;

    @Override
    public void deleteCart(List<Long> longs, PrincipalDetails principalDetails) {
        jpaQueryFactory
                .delete(qCart)
                .where(qCart.board.Id.in(longs),qCart.user.id.eq(principalDetails.getDto().getId()))
                .execute();
    }

    @Override
    public List<Cart> getCarts(List<Long> longs, PrincipalDetails principalDetails) {
        return jpaQueryFactory
                .select(qCart)
                .where(qCart.board.Id.in(longs),qCart.user.id.eq(principalDetails.getDto().getId()))
                .from(qCart)
                .fetch();
    }
}
