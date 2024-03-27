package com.talentmarket.KmongJpa.order.repository;

import com.talentmarket.KmongJpa.order.domain.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRepository extends JpaRepository <OrderItem,Long> {
}
