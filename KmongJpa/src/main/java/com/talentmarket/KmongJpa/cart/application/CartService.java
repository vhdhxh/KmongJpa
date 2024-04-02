package com.talentmarket.KmongJpa.cart.application;

import com.talentmarket.KmongJpa.cart.application.dto.CartRequest;
import com.talentmarket.KmongJpa.cart.application.dto.CartResponse;
import com.talentmarket.KmongJpa.cart.domain.CartItem;
import com.talentmarket.KmongJpa.Item.domain.Item;
import com.talentmarket.KmongJpa.cart.domain.Cart;
import com.talentmarket.KmongJpa.user.domain.Users;
import com.talentmarket.KmongJpa.global.exception.CustomException;
import com.talentmarket.KmongJpa.global.exception.ErrorCode;
import com.talentmarket.KmongJpa.cart.repository.CartItemRepository;
import com.talentmarket.KmongJpa.Item.repository.ItemRepository;
import com.talentmarket.KmongJpa.cart.repository.CartRepository;
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
    private final CartItemRepository cartItemRepository;


    //장바구니 담기
    public void cart (CartRequest cartRequest, Users users) {
        Users.checkUserSession(users);
        Long userId = users.getId();


        Cart cart = cartRepository.findByUserId(userId)
                .orElseGet(() -> {
                    Cart newCart = Cart.builder()
                            .user(users)
                            .build();
                    return cartRepository.save(newCart);
                });

        //cartItem 에서 요청 item과 동일 한 item이 있다면 반환한다.
        Optional<CartItem> optionalCartItem = cart.getCartItems().stream()
                .filter(cartItem -> cartItem.getItem().getId().equals(cartRequest.getItemId()))
                .findFirst();

        //만약 동일한 item 이 존재한다면 그냥 count만 update 처리한다.
        if (optionalCartItem.isPresent()) {
           optionalCartItem.get().updateCount(cartRequest.getCount());
           return;
            // cartItem 처리
        }

        Item item = itemRepository.findById(cartRequest.getItemId()).orElseThrow(() -> new CustomException(ErrorCode.ITEM_NOT_FOUND));
        cart.getCartItems();
        int stockQuantity = item.getStockQuantity();

        int count = cartRequest.getCount();
        if (count <= 0) {
            throw new CustomException(ErrorCode.ITEM_COUNT_ZERO);
        }
        //재고가 만약 count 보다 작다면 수량부족(품절)
        if (stockQuantity == 0) {
            throw new CustomException(ErrorCode.STOCK_SOLD_OUT);
        }
        if (stockQuantity < count) {
            throw new CustomException(ErrorCode.STOCK_NOT_ENOUGH);
        }
        CartItem cartItem = CartItem.createCartItem(cart, item, count);

        cartItemRepository.save(cartItem);

    }


    //장바구니 삭제
    public void deleteCart(List<Long> itemIds, Users user) {
        Users.checkUserSession(user);

       Cart cart = cartRepository.findByUserId(user.getId()).get();

       cartItemRepository.deleteCartItem(cart.getId(),itemIds);



    }

        //장바구니 불러오기
    @Transactional(readOnly = true)
    public List<CartResponse> getCart(Users user) {
        Users.checkUserSession(user);
        Long userId = user.getId();
        Cart cart = cartRepository.findByUserId(userId).get();
        List<CartItem> cartItems = cart.getCartItems();
        List<CartResponse> response = cartItems.stream()
                .map(c->new CartResponse(c.getItem().getTitle(),c.getCount(),c.getItem().getPrice())).collect(Collectors.toList());
       return response;

    }
    }

