package com.talentmarket.KmongJpa.order.application;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.talentmarket.KmongJpa.Item.domain.Item;
import com.talentmarket.KmongJpa.payment.application.*;
import com.talentmarket.KmongJpa.global.exception.CustomException;
import com.talentmarket.KmongJpa.global.exception.ErrorCode;
import com.talentmarket.KmongJpa.Item.repository.ItemRepository;
import com.talentmarket.KmongJpa.order.TempOrderRequest;
import com.talentmarket.KmongJpa.order.TempOrderResponse;
import com.talentmarket.KmongJpa.order.domain.Order;
import com.talentmarket.KmongJpa.order.domain.OrderItem;
import com.talentmarket.KmongJpa.order.repository.OrderItemRepository;
import com.talentmarket.KmongJpa.order.repository.OrderRepository;
import com.talentmarket.KmongJpa.user.domain.Users;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class OrderService {
    private final OrderRepository orderRepository;
    private final ItemRepository itemRepository;
    private final OrderItemRepository orderItemRepository;
    private final ImportClient importClient;

    private static final String imp_key = "1470258353350864";
    private static final String imp_secret = "tJRnFafeZng5NiUj4SJRpTMjr1bhUr3FvSBJVuPSV2iOFtb5BDxMkSGmtGpodMiYshdwrJt9oMRyzANh";

    public String createTempOrder(TempOrderRequest tempOrderRequest , Users user) {

        Users.checkUserSession(user);
        List<Long> itemIds = tempOrderRequest.getTempOrderItems().stream()
                .map(tempOrderItems->tempOrderItems.getItemId())
                .collect(Collectors.toList());
        List<Integer> itemCounts = tempOrderRequest.getTempOrderItems().stream()
                .map(tempOrderItems->tempOrderItems.getItemCount())
                .collect(Collectors.toList());
        List<Item> items = itemRepository.findAllByIdIn(itemIds);
        String uuid = UUID.randomUUID().toString();
        Order order = Order.createOrder(user , uuid);
        orderRepository.save(order);
        List<OrderItem> orderItems = new ArrayList<>();
        //재고 확인 , 주문 상품 추가
        for(int i =0;i<items.size();i++){
            int stockQuantity = items.get(i).getStockQuantity();
            int itemCount = itemCounts.get(i);
            if(stockQuantity==0 || stockQuantity<itemCount)
                throw new CustomException(ErrorCode.STOCK_NOT_ENOUGH);
            OrderItem orderItem = OrderItem.createOrderItem(itemCount,items.get(i),order);
            orderItems.add(orderItem);
        }
        order.addOrderItems(orderItems);
        orderItemRepository.saveAll(orderItems); //TODO:벌크 인서트로 개선하는게 좋겠다.
        return uuid;
    }
    //가주문 불러오기
    public List<TempOrderResponse> getTempOrder(String orderUUID, Users user) {
       Order order = orderRepository.findByUUIDAndUserAndOrderStatus(orderUUID,user.getId(), OrderStatus.Try);
       List<TempOrderResponse> responses = TempOrderResponse.createResponses(order);
       return responses;
    }

    public Boolean afterOrder(PaymentRequest paymentRequest)  {
        int payAmount = paymentRequest.getAmount();
        Order order = orderRepository.findByUuid(paymentRequest.getUuid());
        List<OrderItem> orderItems = orderItemRepository.findByOrderId(order.getId());
        int dbAmount = orderItems.stream()
                .mapToInt(item-> item.getItem().getPrice()*item.getCount())
                .sum();
        if(!amountCheck(payAmount,dbAmount,order, paymentRequest.getImp_uid()))
           return false;
        orderItems.stream().forEach(orderItem->orderItem.getItem().stockReduce(orderItem.getCount()));
        order.updateStatus(OrderStatus.Success);
        return true;
    }

    boolean amountCheck(int payAmount,int dbAmount,Order order,String imp_uid)  {
        if (payAmount != dbAmount) {
            order.updateStatus(OrderStatus.Fail);
            boolean result = false;
            try {
                result = importClient.cancelPayment(imp_uid, payAmount, imp_key, imp_secret);
            } catch (URISyntaxException e) {
                e.printStackTrace();
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
            if (!result) {
                order.updateStatus(OrderStatus.cancelFail);
            }
            return false;
        }
        return true;
    }

    //payment 서비스를 분리해야될거같다.
    private boolean cancelPayment(String imp_uid,int price) throws URISyntaxException, JsonProcessingException {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);


        TokenRequestDto tokenRequest = TokenRequestDto.builder()
                .imp_key(imp_key)
                .imp_secret(imp_secret).build();

        //쌓은 바디를 json형태로 반환
        ObjectMapper objectMapper = new ObjectMapper();
        String body = objectMapper.writeValueAsString(tokenRequest);
        // jsonBody와 헤더 조립
        HttpEntity<String> httpBody = new HttpEntity<>(body, headers);
        RestTemplate restTemplate = new RestTemplate();
        TokenResponseDto response = restTemplate.postForObject(new URI("https://api.iamport.kr/users/getToken"), httpBody, TokenResponseDto.class);
        System.out.println(response.getResponse().getAccess_token());


        String accessToken = response.getResponse().getAccess_token();
        headers.setBearerAuth(accessToken);
        CanselRequest canselRequest = CanselRequest.builder()
                .imp_id(imp_uid)
                .checksum(price)
                .build();
        body = objectMapper.writeValueAsString(canselRequest);

        HttpEntity<String> httpBody2 = new HttpEntity<>(body, headers);

        restTemplate.postForObject(new URI("https://api.iamport.kr/payments/cancel"), httpBody2, TokenResponseDto.class);

   return true;
    }

    // 결제하기 버튼 -> 가주문생성 -> 결제방식 선택후 결제 -> 결제 성공or실패 -> 주문상태 변경

}
