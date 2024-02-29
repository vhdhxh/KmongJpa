package com.talentmarket.KmongJpa.service;

import com.talentmarket.KmongJpa.Dto.CartRequest;
import com.talentmarket.KmongJpa.Dto.CartResponse;
import com.talentmarket.KmongJpa.config.auth.PrincipalDetails;
import com.talentmarket.KmongJpa.entity.Item;
import com.talentmarket.KmongJpa.entity.Cart;
import com.talentmarket.KmongJpa.entity.Users;
import com.talentmarket.KmongJpa.exception.CustomException;
import com.talentmarket.KmongJpa.exception.ErrorCode;
import com.talentmarket.KmongJpa.repository.ItemRepository;
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
    private final ItemRepository itemRepository;

    //장바구니 담기
    public void cart (CartRequest cartRequest, PrincipalDetails principalDetails) {
        Users.checkUserSession(principalDetails);
        Item item = itemRepository.findById(cartRequest.getBoardId()).orElseThrow(()->new CustomException(ErrorCode.ITEM_NOT_FOUND));
        int stockQuantity = item.getStockQuantity();
        int count = cartRequest.getCount();
        //재고가 만약 count 보다 작다면 수량부족(품절)
        if (stockQuantity<count){
            throw new CustomException(ErrorCode.STOCK_SOLD_OUT);
        }
       Optional<Cart> optional = cartRepository.findByItemIdAndUserId(cartRequest.getBoardId(),principalDetails.getDto().getId());
        if (optional.isEmpty()) {
             Cart cart = Cart.createCart(item,count,principalDetails);
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
        List<CartResponse> response = carts.stream().map(c->new CartResponse(c.getItem().getTitle(),c.getCount(),c.getItem().getPrice())).collect(Collectors.toList());

       return response;

    }

}
