package com.talentmarket.KmongJpa.Item.repository;

import com.talentmarket.KmongJpa.Item.application.dto.ItemPaginationDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ItemRepositoryCustom {
    public Page<ItemPaginationDto> paginationCoveringIndex (Pageable pageable, Long total);
}
