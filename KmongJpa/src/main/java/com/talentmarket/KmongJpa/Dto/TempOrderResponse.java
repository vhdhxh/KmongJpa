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
    private String item;
    private int count;
    private int price;


    public static List<TempOrderResponse> createResponses(Order order) {

//        List<String> orderItems = order.getOrderItems().stream()
//                .map(OrderItem->OrderItem.getItem().getTitle())
//                .collect(Collectors.toList());
      List<TempOrderResponse> responses = order.getOrderItems().stream()
              .map(orderItem->TempOrderResponse
                      .builder()
                      .item(orderItem.getItem().getTitle())
                      .count(orderItem.getCount())
                      .price(orderItem.getItem().getPrice())
                      .build())
              .collect(Collectors.toList());


        return responses;
    }

}
