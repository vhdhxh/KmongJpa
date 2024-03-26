package com.talentmarket.KmongJpa.entity;

import com.talentmarket.KmongJpa.Dto.TempOrderRequest;
import com.talentmarket.KmongJpa.repository.ItemRepository;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)
@Table(name = "orderItem")
public class OrderItem {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "orderItem_id")
    private Long id;


    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;

    @ManyToOne
    @JoinColumn(name = "item_id")
    private Item item;

    private int count;

    public static OrderItem createOrderItem(int count , Item item , Order order) {
        return OrderItem.builder()
                .count(count)
                .item(item)
                .order(order)
                .build();
    }





}
