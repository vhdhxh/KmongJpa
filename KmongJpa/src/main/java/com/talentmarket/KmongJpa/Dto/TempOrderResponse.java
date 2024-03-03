package com.talentmarket.KmongJpa.Dto;

import com.talentmarket.KmongJpa.entity.Order;
import com.talentmarket.KmongJpa.entity.OrderItem;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
@Builder
public class TempOrderResponse {
    private List<String> items;


    public static TempOrderResponse create(Order order) {


        List<String> orderItems = order.getOrderItems().stream()
                .map(OrderItem->OrderItem.toString())
                .collect(Collectors.toList());
        return new TempOrderResponse(orderItems);
    }

}
