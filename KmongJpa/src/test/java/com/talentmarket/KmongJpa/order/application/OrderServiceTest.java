package com.talentmarket.KmongJpa.order.application;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.talentmarket.KmongJpa.order.domain.Order;
import com.talentmarket.KmongJpa.payment.application.ImportClient;
import com.talentmarket.KmongJpa.payment.application.PaymentRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.PropertySource;

import java.net.URISyntaxException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

@SpringBootTest
@PropertySource("test")
class OrderServiceTest {
    @Autowired
    private OrderService orderService;
    @MockBean
    private ImportClient importClient;

    @DisplayName("결제가 성공하면 주문 상태가 바뀌고 재고를 차감한다")
    @Test
    void test() throws URISyntaxException, JsonProcessingException {
    //given
        Mockito.when(importClient.cancelPayment(any(String.class),any(Integer.class),any(String.class),any(String.class))).thenReturn(true);
        Order order = Order.builder().uuid("uuid").orderStatus(OrderStatus.Try).user().build();
    //when
        orderService.afterOrder(new PaymentRequest("imp",1000,"uuid"));
    //then

    }

}