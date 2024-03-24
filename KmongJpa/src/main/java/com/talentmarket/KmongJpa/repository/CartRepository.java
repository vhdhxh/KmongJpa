package com.talentmarket.KmongJpa.repository;

import com.talentmarket.KmongJpa.entity.Cart;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface CartRepository extends JpaRepository<Cart,Long>,CartRepositoryCustom {
//    @Query("select c from Cart c where c.item.Id = :itemId and c.user.id = :userId")
//    Optional<Cart> findByItemIdAndUserId(@Param("itemId") Long itemId,@Param("userId") Long userId);

//    @Query("select c from Cart c where c.item.Id = :itemId and c.user.id = :userId")
//    Page<Cart> findPagingCartByItemIdAndUserId(@Param("itemId") Long itemId, @Param("userId") Long userId, Pageable pageable);
//
//    @Query("select c from Cart c where c.user.id = :userId")
//    List<Cart> findCartByUserId (@Param("userId") Long userId);
@Query("select c from Cart c where c.user.id = :userId")
    Optional<Cart> findByUserId (@Param("userId")Long userId);
}
