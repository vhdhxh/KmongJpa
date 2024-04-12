package com.talentmarket.KmongJpa.Item.repository;

import com.talentmarket.KmongJpa.Item.domain.ItemImage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ItemImageRepository extends JpaRepository <ItemImage,Long> {
    List<ItemImage> findByItemId(Long itemId);
}
