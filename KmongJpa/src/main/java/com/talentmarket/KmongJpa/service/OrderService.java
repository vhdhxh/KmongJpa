package com.talentmarket.KmongJpa.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.talentmarket.KmongJpa.Dto.*;
import com.talentmarket.KmongJpa.config.auth.PrincipalDetails;
import com.talentmarket.KmongJpa.entity.*;
import com.talentmarket.KmongJpa.exception.CustomException;
import com.talentmarket.KmongJpa.exception.ErrorCode;
import com.talentmarket.KmongJpa.repository.ItemRepository;
import com.talentmarket.KmongJpa.repository.OrderItemRepository;
import com.talentmarket.KmongJpa.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final ItemRepository itemRepository;
    private final OrderItemRepository orderItemRepository;

    private static final String imp_key = "1470258353350864";
    private static final String imp_secret = "tJRnFafeZng5NiUj4SJRpTMjr1bhUr3FvSBJVuPSV2iOFtb5BDxMkSGmtGpodMiYshdwrJt9oMRyzANh";
    // 장바구니나 게시글에서
    // 결제하기 버튼을 누르면
    // 가주문 생성
    public String createTempOrder(TempOrderRequest tempOrderRequest , PrincipalDetails principalDetails) {
        Users.checkUserSession(principalDetails);
        // 1.요청으로 주문상품을 받아 주문상품 생성.
        // 2.가주문 생성
        Users user = principalDetails.getDto();

        List<Long> itemIds = tempOrderRequest.getTempOrderItems().stream()
                .map(tempOrderItems->tempOrderItems.getItemId())
                .collect(Collectors.toList());

        List<Integer> itemCounts = tempOrderRequest.getTempOrderItems().stream()
                .map(tempOrderItems->tempOrderItems.getItemCount())
                .collect(Collectors.toList());

      List<Item> item = itemRepository.findAllByIdIn(itemIds);
      //재고 확인


        String uuid = UUID.randomUUID().toString();

        Order order = Order.createOrder(user , uuid);

       orderRepository.save(order);

        List<OrderItem> orderItems = new ArrayList<>();

        //재고 확인 , 주문 상품 추가
        for(int i =0;i<item.size();i++){
            int stockQuantity = item.get(i).getStockQuantity();
            int itemCount = itemCounts.get(i);
            if(stockQuantity==0 && stockQuantity<itemCount)
                throw new CustomException(ErrorCode.STOCK_NOT_ENOUGH);
            OrderItem orderItem = OrderItem.createOrderItem(itemCount,item.get(i),order);
            orderItems.add(orderItem);
        }
        //벌크 인서트로 개선하는게 좋겠다.
        orderItemRepository.saveAll(orderItems);

        return uuid;
    }
    //가주문 불러오기
    public List<TempOrderResponse> getTempOrder(String orderUUID, PrincipalDetails principalDetails) {
        Users user = principalDetails.getDto();
       Order order = orderRepository.findByUUIDAndUserAndOrderStatus(orderUUID,user.getId(),OrderStatus.Try);
      List<TempOrderResponse> responses = TempOrderResponse.createResponses(order);
         return responses;
    }

    // 결제가 (성공이든 실패든 처리되면)
    // 주문 상태 변경
    // 성공일 경우 포트원 서버 와 DB 주문 가격을 검증
    //주문을 성공 했을경우 true , 실패했을경우 false 반환
    public Boolean afterOrder(PaymentRequest paymentRequest) throws JsonProcessingException, URISyntaxException {
        // 포트원 서버 와 DB 주문 가격을 검증

        int payAmount = paymentRequest.getAmount();
        Order order = orderRepository.findByUuid(paymentRequest.getUuid());
        int orderItemCount = order.getOrderItems().size();
        int dbAmount = 0;

       dbAmount = order.getOrderItems().stream()
                .mapToInt(item -> item.getItem().getPrice())
                .sum();
//        for (int i = 0; i < orderItemCount; i++) {
//            dbAmount += order.getOrderItems().get(i).getItem().getPrice();
//
//        }
        // 가격이 서로 다르다면 결제 취소 요청
        if (payAmount != dbAmount) {
            order.updateStatus(OrderStatus.Fail);  //이부분은 아래 예외가 터지면 어차피 롤백되어서 의미가 없는거같다. 그럼어떻게?
            boolean result = cancelPayment(paymentRequest.getImp_uid(), payAmount);  //외부 api인데 트랜잭션을 어떻게 적용하는게 좋을까? 만약 취소 요청 자체가 실패한다면?

            return false;
            //예외를 던지지말고 차라리 response를 return할까?
        }
        // 가격이 맞다면 주문 상태 변경 후 재고차감 그런데, 재고차감을 이시점에 하는게맞나?

        for (int i = 0; i < orderItemCount; i++) {
            int count = order.getOrderItems().get(i).getCount();
            order.getOrderItems().get(i).getItem().stockReduce(count);
        }

        order.updateStatus(OrderStatus.Success);

        //아니면 검증메소드();
        //      결제취소메소드();
        //

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
