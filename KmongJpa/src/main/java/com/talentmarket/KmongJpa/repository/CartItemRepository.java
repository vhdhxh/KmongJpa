package com.talentmarket.KmongJpa.repository;

import com.talentmarket.KmongJpa.entity.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CartItemRepository extends JpaRepository<CartItem,Long> {


//    @Query("delete from CartItem c where c.id = :cartId and c.Item.Id in :itemIds")
//    @Modifying
//    void deleteCartItem(@Param("cartId") Long cartId,@Param("itemIds") List<Long> itemIds);
    @Query("delete from CartItem c where c.cart.id = :cartId and c.item.Id in :itemIds")
    @Modifying
    void deleteCartItem(@Param("cartId") Long cartId,@Param("itemIds") List<Long> itemIds);
}
