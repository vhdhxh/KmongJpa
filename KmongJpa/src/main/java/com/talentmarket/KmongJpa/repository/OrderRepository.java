package com.talentmarket.KmongJpa.repository;

import com.talentmarket.KmongJpa.entity.Cart;
import com.talentmarket.KmongJpa.entity.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order,Long> {



    @Query("select o from Order o where o.uuid = :uuid and o.user.id = :userId and o.orderStatus = :status")
    public Order findByUUIDAndUserAndOrderStatus(@Param("uuid")String uuid
            , @Param("userId") Long userId,
              @Param("status")String status);
}
