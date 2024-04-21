package com.talentmarket.KmongJpa.cart.application;

import com.talentmarket.KmongJpa.cart.application.dto.CartRequest;
import com.talentmarket.KmongJpa.cart.application.dto.CartResponse;
import com.talentmarket.KmongJpa.cart.domain.CartItem;
import com.talentmarket.KmongJpa.Item.domain.Item;
import com.talentmarket.KmongJpa.cart.domain.Cart;
import com.talentmarket.KmongJpa.cart.domain.CartItems;
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
    public void cart(CartRequest cartRequest, Users users) {
        Users.checkUserSession(users);
        Long userId = users.getId();
        Long itemId = cartRequest.getItemId();
        int ItemCount = cartRequest.getCount();
        Cart cart = findCartByUserId(userId, users);
        addOrUpdateCartItem(cart,itemId,ItemCount);
        Item item = itemRepository.findById(cartRequest.getItemId()).orElseThrow(() -> new CustomException(ErrorCode.ITEM_NOT_FOUND));
        cart.getCartItems();
        validateCount(ItemCount);
        //재고가 만약 count 보다 작다면 수량부족(품절)
        if (!item.soldOutCheck()) {
            throw new CustomException(ErrorCode.STOCK_SOLD_OUT);
        }
        if (!item.stockCheck(ItemCount)) {
            throw new CustomException(ErrorCode.STOCK_NOT_ENOUGH);
        }
        CartItem cartItem = CartItem.createCartItem(cart, item, ItemCount);

        cartItemRepository.save(cartItem);
    }

    //장바구니 삭제
    public void deleteCart(List<Long> itemIds, Users user) {
        Users.checkUserSession(user);
        Cart cart = cartRepository.findByUserId(user.getId()).get();
        cartItemRepository.deleteCartItem(cart.getId(), itemIds);
    }

    //장바구니 불러오기
    @Transactional(readOnly = true)
    public List<CartResponse> getCart(Users user) {
        Users.checkUserSession(user);
        Long userId = user.getId();
        Cart cart = cartRepository.findByUserId(userId).get();
        CartItems cartItems = new CartItems(cart.getCartItems());
        List<CartResponse> response = cartItems.toResponse();
        return response;
        //        List<CartItem> cartItems = cart.getCartItems();
//        List<CartResponse> response = cartItems.stream()
//                .map(c -> new CartResponse(c.getItem().getTitle(), c.getCount(), c.getItem().getPrice())).collect(Collectors.toList());
    }

    private void validateCount(int ItemCount) {
        if (ItemCount <= 0) {
            throw new CustomException(ErrorCode.ITEM_COUNT_ZERO);
        }
    }

    private Cart findCartByUserId(Long userId, Users users) {
        return cartRepository.findByUserId(userId)
                .orElseGet(() -> {
                    Cart newCart = Cart.builder()
                            .user(users)
                            .build();
                    return cartRepository.save(newCart);
                });
    }

    private void addOrUpdateCartItem(Cart cart, Long itemId, int ItemCount) {
        Optional<CartItem> existingCartItem = cart.getCartItems().stream()
                .filter(cartItem -> cartItem.getItem().getId().equals(itemId))
                .findFirst();
        if (existingCartItem.isPresent())
            existingCartItem.get().updateCount(ItemCount);
    }
}

