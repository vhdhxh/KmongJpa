package com.talentmarket.KmongJpa.order;

import com.talentmarket.KmongJpa.order.domain.Order;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

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
