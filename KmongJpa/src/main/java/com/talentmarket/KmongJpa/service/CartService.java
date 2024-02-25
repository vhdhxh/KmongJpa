package com.talentmarket.KmongJpa.service;

import com.talentmarket.KmongJpa.Dto.CartDto;
import com.talentmarket.KmongJpa.config.auth.PrincipalDetails;
import com.talentmarket.KmongJpa.entity.Board;
import com.talentmarket.KmongJpa.entity.Cart;
import com.talentmarket.KmongJpa.entity.Users;
import com.talentmarket.KmongJpa.exception.CustomException;
import com.talentmarket.KmongJpa.exception.ErrorCode;
import com.talentmarket.KmongJpa.repository.BoardRepository;
import com.talentmarket.KmongJpa.repository.CartRepositoryCustom;
import com.talentmarket.KmongJpa.repository.CartRepository;
import com.talentmarket.KmongJpa.repository.CartRepositoryCustomImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class CartService {
    private final CartRepository cartRepository;
    private final BoardRepository boardRepository;

    //장바구니 담기
    public void cart (CartDto cartDto, PrincipalDetails principalDetails) {
        Users.checkUserSession(principalDetails);
        int count = cartDto.getCount();
        Board board = boardRepository.findById(cartDto.getBoardId()).orElseThrow(()->new CustomException(ErrorCode.BOARD_NOT_FOUND));
        Cart cart = Cart.createCart( board,count,principalDetails);
        cartRepository.save(cart);
    }

    //장바구니 삭제
    public void deleteCart(List<Long> boardIds,PrincipalDetails principalDetails) {
        Users.checkUserSession(principalDetails);
        cartRepository.deleteCart(boardIds);
    }

}
