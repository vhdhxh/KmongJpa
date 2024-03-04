package com.talentmarket.KmongJpa.repository;

import com.talentmarket.KmongJpa.entity.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRepository extends JpaRepository <OrderItem,Long> {
}
