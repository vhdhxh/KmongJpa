package com.talentmarket.KmongJpa.order;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TempOrderItem {
    private Long itemId;
    private int itemCount;
}
