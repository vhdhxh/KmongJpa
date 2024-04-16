package com.talentmarket.KmongJpa.order.application;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.talentmarket.KmongJpa.Item.domain.Item;
import com.talentmarket.KmongJpa.Item.repository.ItemRepository;
import com.talentmarket.KmongJpa.order.domain.Order;
import com.talentmarket.KmongJpa.order.domain.OrderItem;
import com.talentmarket.KmongJpa.order.repository.OrderItemRepository;
import com.talentmarket.KmongJpa.order.repository.OrderRepository;
import com.talentmarket.KmongJpa.payment.application.ImportClient;
import com.talentmarket.KmongJpa.payment.application.PaymentRequest;
import com.talentmarket.KmongJpa.user.domain.Users;
import com.talentmarket.KmongJpa.user.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.PropertySource;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;

import java.net.URISyntaxException;


import static org.assertj.core.api.ClassBasedNavigableIterableAssert.assertThat;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;

@SpringBootTest
@ActiveProfiles("test")
class OrderServiceTest {
    @Autowired
    private OrderService orderService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private OrderItemRepository orderItemRepository;
    @Autowired
    ItemRepository itemRepository;
    @MockBean
    private ImportClient importClient;

    @DisplayName("결제가 성공하면 주문 상태가 바뀌고 재고를 차감한다")
    @Test
    void test() throws URISyntaxException, JsonProcessingException {
    //given
        Mockito.when(importClient.cancelPayment(any(String.class),any(Integer.class),any(String.class),any(String.class))).thenReturn(true);
        Users user = Users.builder().name("test").email("test").build();
        userRepository.save(user);
        Item item = Item.builder().title("testItem").price(1000).stockQuantity(3).build();
        itemRepository.save(item);


        Order order = Order.builder().uuid("uuid").orderStatus(OrderStatus.Try).user(user).build();
        orderRepository.save(order);
        OrderItem orderItem = OrderItem.createOrderItem(1,item,order);
        orderItemRepository.save(orderItem);


    //when
        orderService.afterOrder(new PaymentRequest("imp",1000,"uuid"));
    //then
       Order order1 = orderRepository.findByUuid("uuid");
       Item item1 = itemRepository.findById(1L).get();
       assertThat(order1.getOrderStatus()).isEqualTo(OrderStatus.Success);
       assertThat(item1.getStockQuantity()).isEqualTo(2);

    }

    @DisplayName("결제 가격이 다를 시 환불을 하고 환불에 실패하면 주문상태가 환불 실패로 바뀐다.")
    @Test
    void test2() throws URISyntaxException, JsonProcessingException {
        //given
        Mockito.when(importClient.cancelPayment(any(String.class),any(Integer.class),any(String.class),any(String.class))).thenReturn(false);
        Users user = Users.builder().name("test").email("test").build();
        userRepository.save(user);
        Item item = Item.builder().title("testItem").price(1000).stockQuantity(3).build();
        itemRepository.save(item);


        Order order = Order.builder().uuid("uuid").orderStatus(OrderStatus.Try).user(user).build();
        orderRepository.save(order);
        OrderItem orderItem = OrderItem.createOrderItem(1,item,order);
        orderItemRepository.save(orderItem);


        //when
        orderService.afterOrder(new PaymentRequest("imp",900,"uuid"));
        //then
        Order order1 = orderRepository.findByUuid("uuid");
        Item item1 = itemRepository.findById(1L).get();
        assertThat(order1.getOrderStatus()).isEqualTo(OrderStatus.cancelFail);
        assertThat(item1.getStockQuantity()).isEqualTo(3);

    }

}