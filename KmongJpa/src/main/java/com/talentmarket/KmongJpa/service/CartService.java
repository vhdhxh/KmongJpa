package com.talentmarket.KmongJpa.service;

import com.talentmarket.KmongJpa.Dto.CartRequest;
import com.talentmarket.KmongJpa.Dto.CartResponse;
import com.talentmarket.KmongJpa.config.auth.PrincipalDetails;
import com.talentmarket.KmongJpa.entity.Board;
import com.talentmarket.KmongJpa.entity.Cart;
import com.talentmarket.KmongJpa.entity.Users;
import com.talentmarket.KmongJpa.exception.CustomException;
import com.talentmarket.KmongJpa.exception.ErrorCode;
import com.talentmarket.KmongJpa.repository.BoardRepository;
import com.talentmarket.KmongJpa.repository.CartRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
@Log4j2
public class CartService {
    private final CartRepository cartRepository;
    private final BoardRepository boardRepository;

    //장바구니 담기
    public void cart (CartRequest cartRequest, PrincipalDetails principalDetails) {
        Users.checkUserSession(principalDetails);
        Board board = boardRepository.findById(cartRequest.getBoardId()).orElseThrow(()->new CustomException(ErrorCode.BOARD_NOT_FOUND));
        int count = cartRequest.getCount();
       Optional<Cart> optional = cartRepository.findByBoardIdAndUserId(cartRequest.getBoardId(),principalDetails.getDto().getId());
        if (optional.isEmpty()) {
             Cart cart = Cart.createCart( board,count,principalDetails);
            cartRepository.save(cart);
        } else
            optional.get().updateCount(count);
    }

    //장바구니 삭제
    public void deleteCart(List<Long> boardIds,PrincipalDetails principalDetails) {
        Users.checkUserSession(principalDetails);
        if(cartRepository.getCarts(boardIds,principalDetails).isEmpty()){
            log.info(cartRepository.getCarts(boardIds,principalDetails));
            throw new CustomException(ErrorCode.CART_NOT_FOUND);
        }

        cartRepository.deleteCart(boardIds,principalDetails);
    }

    //장바구니 불러오기
    @Transactional(readOnly = true)
    public List<CartResponse> getCart(PrincipalDetails principalDetails) {
        Users.checkUserSession(principalDetails);
        Long userId = principalDetails.getDto().getId();
       List<Cart> carts = cartRepository.findCartByUserId(userId);
        List<CartResponse> response = carts.stream().map(c->new CartResponse(c.getBoard().getTitle(),c.getCount(),c.getBoard().getPrice())).collect(Collectors.toList());
       return response;

    }

}
