package com.talentmarket.KmongJpa.order.repository;

import com.talentmarket.KmongJpa.order.application.OrderStatus;
import com.talentmarket.KmongJpa.order.domain.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface OrderRepository extends JpaRepository<Order,Long> {



    @Query("select o from Order o where o.uuid = :uuid and o.user.id = :userId and o.orderStatus = :status")
    public Order findByUUIDAndUserAndOrderStatus(@Param("uuid")String uuid
            , @Param("userId") Long userId,
              @Param("status") OrderStatus orderStatus);

    Order findByUuid (String uuid);
}
