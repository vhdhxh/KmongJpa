package com.talentmarket.KmongJpa.Item.repository;

import com.talentmarket.KmongJpa.Item.application.dto.ItemPaginationDto;
import com.talentmarket.KmongJpa.Item.domain.Item;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ItemRepositoryCustom {
    public Page<ItemPaginationDto> paginationCoveringIndex (Pageable pageable, Long total);
    public Page search(Pageable pageable , String title , String category , String sort);
}
