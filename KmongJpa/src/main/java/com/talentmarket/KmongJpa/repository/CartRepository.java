package com.talentmarket.KmongJpa.repository;

import com.talentmarket.KmongJpa.entity.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepository extends JpaRepository<Cart,Long> {
}
