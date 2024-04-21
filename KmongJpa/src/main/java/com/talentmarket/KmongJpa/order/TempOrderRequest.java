package com.talentmarket.KmongJpa.order;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
@AllArgsConstructor
public class TempOrderRequest {
    List<TempOrderItem> tempOrderItems;
}
