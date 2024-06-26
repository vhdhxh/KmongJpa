package com.talentmarket.KmongJpa.order.application;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.talentmarket.KmongJpa.Item.domain.Item;
import com.talentmarket.KmongJpa.Item.repository.ItemRepository;
import com.talentmarket.KmongJpa.global.exception.CustomException;
import com.talentmarket.KmongJpa.global.exception.ErrorCode;
import com.talentmarket.KmongJpa.order.TempOrderItem;
import com.talentmarket.KmongJpa.order.TempOrderRequest;
import com.talentmarket.KmongJpa.order.TempOrderResponse;
import com.talentmarket.KmongJpa.order.domain.Order;
import com.talentmarket.KmongJpa.order.domain.OrderItem;
import com.talentmarket.KmongJpa.order.repository.OrderItemRepository;
import com.talentmarket.KmongJpa.order.repository.OrderRepository;
import com.talentmarket.KmongJpa.payment.application.ImportClient;
import com.talentmarket.KmongJpa.payment.application.PaymentRequest;
import com.talentmarket.KmongJpa.user.domain.Users;
import com.talentmarket.KmongJpa.user.repository.UserRepository;
import jakarta.persistence.OptimisticLockException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.PropertySource;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;


import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.ClassBasedNavigableIterableAssert.assertThat;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.ArgumentMatchers.any;

@SpringBootTest
@Transactional
//@Rollback(value = false)
@ActiveProfiles("test2")
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
    private ItemRepository itemRepository;
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
        Item item2 =  itemRepository.save(item);
        Order order = Order.builder().uuid("uuid").orderStatus(OrderStatus.Try).user(user).build();
        orderRepository.save(order);
        OrderItem orderItem = OrderItem.createOrderItem(2,item,order);
        orderItemRepository.save(orderItem);
    //when
        orderService.afterOrder(new PaymentRequest("imp",2000,"uuid"));
    //then
       Order order1 = orderRepository.findByUuid("uuid");
       Item item1 = itemRepository.findById(item2.getId()).get();
       assertThat(order1.getOrderStatus()).isEqualTo(OrderStatus.Success);
       assertThat(item1.getStockQuantity()).isEqualTo(1);

    }

    @DisplayName("결제 가격이 다를 시 환불을 하고 환불에 실패하면 주문상태가 환불 실패로 바뀐다.")
    @Test
    void test2() throws URISyntaxException, JsonProcessingException {
        //given
        Mockito.when(importClient.cancelPayment(any(String.class),any(Integer.class),any(String.class),any(String.class))).thenReturn(false);
        Users user = Users.builder().name("test").email("test").build();
        userRepository.save(user);
        Item item = Item.builder().title("testItem").price(1000).stockQuantity(3).build();
        Item item2 = itemRepository.save(item);
        Order order = Order.builder().uuid("uuid").orderStatus(OrderStatus.Try).user(user).build();
        orderRepository.save(order);
        OrderItem orderItem = OrderItem.createOrderItem(1,item,order);
        orderItemRepository.save(orderItem);
        //when
        boolean result = orderService.afterOrder(new PaymentRequest("imp",900,"uuid"));
        //then
        Order order1 = orderRepository.findByUuid("uuid");
        Item item1 = itemRepository.findById(item2.getId()).get();
        assertThat(order1.getOrderStatus()).isEqualTo(OrderStatus.cancelFail);
        assertThat(item1.getStockQuantity()).isEqualTo(3);
        assertThat(result).isFalse();

    }

    @DisplayName("가주문을 생성한다.")
    @Test
    void test3() throws InterruptedException {
    //given
        itemRepository.saveAndFlush(Item.builder().Id(1L).stockQuantity(3).build());
        Thread.sleep(3000);
        Users user =  Users.builder().build();
        TempOrderItem tempOrderItem = new TempOrderItem(1L,3);
        List<TempOrderItem> tempOrderItems = new ArrayList<>();
        tempOrderItems.add(tempOrderItem);
        userRepository.save(user);
    //when
    String uuid = orderService.createTempOrder(new TempOrderRequest(tempOrderItems),user);
    Order order =  orderRepository.findByUuid(uuid);
    //then
   assertThat(order.getOrderStatus()).isEqualTo(OrderStatus.Try);
   assertThat(order.getOrderItems().get(0).getCount()).isEqualTo(3);
   assertThat(order.getOrderItems().get(0).getItem().getId()).isEqualTo(1L);
    }

    @DisplayName("가주문 생성시 재고보다 많은 수량을 주문하면 예외가 터진다..")
    @Test
    void test4() {
        //given
        itemRepository.save(Item.builder().Id(1L).stockQuantity(3).build());
        Users user =  Users.builder().build();
        TempOrderItem tempOrderItem = new TempOrderItem(1L,5);
        List<TempOrderItem> tempOrderItems = new ArrayList<>();
        tempOrderItems.add(tempOrderItem);
        userRepository.save(user);
        //when //then
        assertThatThrownBy(()->orderService.createTempOrder(new TempOrderRequest(tempOrderItems),user)).isInstanceOf(CustomException.class)
                .hasMessage("재고 수량이 부족합니다.");
    }

    @DisplayName("가주문을 불러온다..")
    @Test
    void test5() {
        //given
        itemRepository.save(Item.builder().Id(1L).title("testItemName").price(1000).stockQuantity(3).build());
        Users user =  Users.builder().build();
        TempOrderItem tempOrderItem = new TempOrderItem(1L,3);
        List<TempOrderItem> tempOrderItems = new ArrayList<>();
        tempOrderItems.add(tempOrderItem);
        userRepository.save(user);
        //when
        String uuid = orderService.createTempOrder(new TempOrderRequest(tempOrderItems),user);
       List<TempOrderResponse> tempOrderResponses  = orderService.getTempOrder(uuid,user);
        //then
        assertThat(tempOrderResponses.get(0).getItem()).isEqualTo("testItemName");
        assertThat(tempOrderResponses.get(0).getPrice()).isEqualTo(1000);
        assertThat(tempOrderResponses.get(0).getCount()).isEqualTo(3);
    }

    @DisplayName("재고보다 많은양의 구매가 들어왔을 경우 예외를 던지며 구매에 실패한다")
    @Test
    void 재고동시성테스트() throws URISyntaxException, JsonProcessingException, InterruptedException {
        //    //given

        int numThreads = 30;
        Mockito.when(importClient.cancelPayment(any(String.class),any(Integer.class),any(String.class),any(String.class))).thenReturn(true);
        CountDownLatch doneSignal = new CountDownLatch(numThreads);
        ExecutorService executorService = Executors.newFixedThreadPool(numThreads);
        AtomicInteger successCount = new AtomicInteger();
        AtomicInteger failCount = new AtomicInteger();
        executorService.execute(()->{
//            itemRepository.save(Item.builder().version(1).Id(1L).price(1000).stockQuantity(10).title("apple").build());
        });
      Thread.sleep(10000);
    //when
        for (int i = 0; i < numThreads; i++) {
            int finalI = i;
            executorService.execute(() -> {
                try {
                    Order order = Order.builder().uuid("uuid"+finalI).orderStatus(OrderStatus.Try).build();
                    orderRepository.save(order);
                    OrderItem orderItem = OrderItem.createOrderItem(1,itemRepository.findById(1L).orElseThrow(),order);
                    orderItemRepository.save(orderItem);
                    boolean result = orderService.afterOrder(new PaymentRequest("imp", 1000, "uuid" + finalI));
                    successCount.getAndIncrement();
                } catch (OptimisticLockingFailureException e) {
                    e.printStackTrace();
                    failCount.getAndIncrement();
                } finally {
                    doneSignal.countDown();
                }
            });
        }
        doneSignal.await();
        executorService.shutdown();
    //then
        assertAll(
                () -> assertThat(successCount.get()).isEqualTo(2),
                () -> assertThat(failCount.get()).isEqualTo(8)
        );
    }


}