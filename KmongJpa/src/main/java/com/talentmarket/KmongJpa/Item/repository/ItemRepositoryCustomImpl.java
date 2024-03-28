package com.talentmarket.KmongJpa.Item.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.talentmarket.KmongJpa.Item.application.dto.ItemPaginationDto;
import com.talentmarket.KmongJpa.Item.domain.QItem;
import com.talentmarket.KmongJpa.user.domain.QUsers;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;

@RequiredArgsConstructor
public class ItemRepositoryCustomImpl implements ItemRepositoryCustom {
    private final JPAQueryFactory jpaQueryFactory;

    public Page<ItemPaginationDto> paginationCoveringIndex (Pageable pageable,Long total) {
        QItem item = QItem.item;
        QUsers user = QUsers.users;
        List <Long> ids = jpaQueryFactory.select(item.Id)
                .from(item)
                .limit(pageable.getPageSize())
                .offset(pageable.getPageNumber())
                .fetch();

        List<ItemPaginationDto> items = jpaQueryFactory.select(Projections.fields(ItemPaginationDto.class,
                        item.Id.as("itemId"),item.title,item.users.name,item.price))
                .from(item)
                .innerJoin(item.users,user)
                .where(item.Id.in(ids))
                .orderBy(item.Id.desc())
                .fetch();
         return new PageImpl<>(items,pageable,total);

    }
}
