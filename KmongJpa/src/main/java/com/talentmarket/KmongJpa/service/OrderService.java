package com.talentmarket.KmongJpa.service;

import com.talentmarket.KmongJpa.Dto.TempOrderRequest;
import com.talentmarket.KmongJpa.Dto.TempOrderResponse;
import com.talentmarket.KmongJpa.config.auth.PrincipalDetails;
import com.talentmarket.KmongJpa.entity.Order;
import com.talentmarket.KmongJpa.entity.OrderItem;
import com.talentmarket.KmongJpa.entity.OrderStatus;
import com.talentmarket.KmongJpa.entity.Users;
import com.talentmarket.KmongJpa.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    // 장바구니나 게시글에서
    // 결제하기 버튼을 누르면
    // 가주문 생성
    public String createTempOrder(TempOrderRequest tempOrderRequest , PrincipalDetails principalDetails) {
        OrderItem orderItem =

        String uuid = UUID.randomUUID().toString();
        Order order = Order.builder()
                .orderStatus(OrderStatus.Try)
                .orderItems(orderItemList)
                .user(principalDetails.getDto())
                .build();
       orderRepository.save(order);
        return uuid;
    }
    //가주문 불러오기
    public TempOrderResponse getTempOrder(String orderUUID, PrincipalDetails principalDetails) {
        Users user = principalDetails.getDto();
       Order order = orderRepository.findByUUIDAndUserAndOrderStatus(orderUUID,user.getId(),"Try");
      TempOrderResponse tempOrderResponse = TempOrderResponse.create(order);
         return tempOrderResponse;
    }

    // 결제가 성공하면
    // 주문 상태 변경
    public void updateOrder(Long orderId) {
        Order order = orderRepository.findById(orderId).orElseThrow();

    }

    // 결제하기 버튼 -> 가주문생성 -> 결제방식 선택후 결제 -> 결제 성공or실패 -> 주문상태 변경

}
