package com.talentmarket.KmongJpa.order.presentation;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.talentmarket.KmongJpa.global.ApiResponse;
import com.talentmarket.KmongJpa.payment.application.PaymentRequest;
import com.talentmarket.KmongJpa.order.TempOrderRequest;
import com.talentmarket.KmongJpa.order.TempOrderResponse;
import com.talentmarket.KmongJpa.global.auth.PrincipalDetails;
import com.talentmarket.KmongJpa.order.application.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.net.URISyntaxException;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class OrderApi {
    private final OrderService orderService;

    @PostMapping("/api/v1/order")
    public ApiResponse createTempOrder(@RequestBody TempOrderRequest tempOrderRequest , @AuthenticationPrincipal PrincipalDetails principalDetails) {

        String uuid = orderService.createTempOrder(tempOrderRequest,principalDetails);
        return ApiResponse.ok(uuid);
    }

    @GetMapping("/api/v1/order/{uuid}")
    public ApiResponse getTempOrder(@PathVariable("uuid") String uuid ,@AuthenticationPrincipal PrincipalDetails principalDetails) {
      List<TempOrderResponse> tempOrderResponse = orderService.getTempOrder(uuid, principalDetails);
        return ApiResponse.ok(tempOrderResponse);
    }

    @PostMapping("/api/v1/getToken")
    public ApiResponse getToken(PaymentRequest paymentRequest) throws URISyntaxException, JsonProcessingException {
        orderService.afterOrder(paymentRequest);
        return ApiResponse.ok(null);
    }


}
