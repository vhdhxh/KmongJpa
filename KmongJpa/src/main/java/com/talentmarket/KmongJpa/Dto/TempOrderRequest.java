package com.talentmarket.KmongJpa.Dto;

import com.talentmarket.KmongJpa.entity.OrderItem;
import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;

@Data
public class TempOrderRequest {
    List<TempOrderItems> tempOrderItems;


    public static class  TempOrderItems {
        private Long itemId;
        private int itemCount;
    }

}
