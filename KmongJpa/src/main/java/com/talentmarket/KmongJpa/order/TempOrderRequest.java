package com.talentmarket.KmongJpa.order;

import lombok.Data;

import java.util.List;

@Data
public class TempOrderRequest {
    List<TempOrderItems> tempOrderItems;

    @Data
    public static class TempOrderItems {
        private Long itemId;
        private int itemCount;
    }

}
