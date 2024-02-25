package com.talentmarket.KmongJpa.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.talentmarket.KmongJpa.entity.QBoard;
import com.talentmarket.KmongJpa.entity.QCart;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@RequiredArgsConstructor
public class CartRepositoryCustomImpl implements CartRepositoryCustom {
    private final JPAQueryFactory jpaQueryFactory;
    QCart qCart = QCart.cart;
    QBoard qBoard = QBoard.board;

    @Override
    public void deleteCart(List<Long> longs) {
        jpaQueryFactory
                .delete(qCart)
                .where(qCart.board.Id.in(longs))
                .execute();
    }
}
