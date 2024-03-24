package com.talentmarket.KmongJpa.repository;

import com.talentmarket.KmongJpa.Dto.ItemPaginationDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ItemRepositoryCustom {
    public Page<ItemPaginationDto> paginationCoveringIndex (Pageable pageable, Long total);
}
