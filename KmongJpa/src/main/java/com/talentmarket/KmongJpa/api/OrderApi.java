package com.talentmarket.KmongJpa.api;

import com.talentmarket.KmongJpa.Dto.ApiResponse;
import com.talentmarket.KmongJpa.Dto.TempOrderRequest;
import com.talentmarket.KmongJpa.Dto.TempOrderResponse;
import com.talentmarket.KmongJpa.config.auth.PrincipalDetails;
import com.talentmarket.KmongJpa.entity.OrderItem;
import com.talentmarket.KmongJpa.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class OrderApi {
    private final OrderService orderService;

    public ApiResponse<TempOrderResponse> createTempOrder(@RequestBody TempOrderRequest tempOrderRequest , @AuthenticationPrincipal PrincipalDetails principalDetails) {

        TempOrderResponse tempOrderResponse = orderService.createTempOrder(tempOrderRequest,principalDetails);
        return ApiResponse.ok(tempOrderResponse);
    }
}
