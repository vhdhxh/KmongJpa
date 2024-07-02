package com.talentmarket.KmongJpa.Item.repository;

import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.talentmarket.KmongJpa.Item.application.dto.ItemPaginationDto;
import com.talentmarket.KmongJpa.Item.domain.Item;
import com.talentmarket.KmongJpa.Item.domain.QItem;
import com.talentmarket.KmongJpa.Item.presentation.SortType;
import com.talentmarket.KmongJpa.user.domain.QUsers;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.util.StringUtils;

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
// select itemId , title , name ,price from item join user where item.id in () order by item.id desc
    }

    //sort :   최신순 , 저가순 , 고가순
    public Page<ItemPaginationDto> search(Pageable pageable ,String title ,String category, String sort) {
        //기본적으로 , 처음에 검색하게되면 title 만 입력받음 정렬은 최신이 default   sort = date , price_desc , price_asc
        //
        QItem item = QItem.item;
        int count = jpaQueryFactory.select(item.Id)
                .from(item)
                .where(titleLike(title))
                .fetch().size();
        System.out.println("count = " + count);
        List <Long> ids = jpaQueryFactory.select(item.Id)
                .from(item)
                .limit(pageable.getPageSize())
                .offset(pageable.getPageNumber())
                .where(titleLike(title),categoryLike(category))
                .fetch();
        OrderSpecifier order = null;
        System.out.println("ids = " +ids);
        if (sort == null || "date".equals(sort)) {
            order = new OrderSpecifier<>(Order.DESC, item.Id);
        }
        if (sort=="price_desc"){
            order = new OrderSpecifier<>(Order.DESC , item.price);
        }
        if(sort=="price_asc"){
            order = new OrderSpecifier<>(Order.ASC , item.price);
        }
        List<ItemPaginationDto> items = jpaQueryFactory.select(Projections.fields(ItemPaginationDto.class,
                        item.Id.as("itemId"),item.title,item.price))
                .from(item)
                .where(item.Id.in(ids)) //category 가 들어갈수도 , 안들어갈수도 있음 안들어갔다면 title 우선
                .orderBy(order)
                .fetch();
        System.out.println("items = " + items);
        return new PageImpl<>(items,pageable,count);
    }
    private BooleanExpression titleLike(String title) {
        QItem item = QItem.item;
        return StringUtils.hasText(title) ? item.title.contains(title) : null;
    }
    private BooleanExpression categoryLike(String category) {
        QItem item = QItem.item;

        return StringUtils.hasText(category) ? item.title.contains(category) : null;
    }
}
