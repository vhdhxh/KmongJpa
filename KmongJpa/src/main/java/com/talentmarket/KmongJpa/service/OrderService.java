package com.talentmarket.KmongJpa.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.talentmarket.KmongJpa.Dto.TempOrderRequest;
import com.talentmarket.KmongJpa.Dto.TempOrderResponse;
import com.talentmarket.KmongJpa.Dto.TokenRequestDto;
import com.talentmarket.KmongJpa.Dto.TokenResponseDto;
import com.talentmarket.KmongJpa.config.auth.PrincipalDetails;
import com.talentmarket.KmongJpa.entity.*;
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

        List<Long> itemIds = tempOrderRequest.getTempOrderItems().stream()
                .map(tempOrderItems->tempOrderItems.getItemId())
                .collect(Collectors.toList());

        List<Integer> itemCounts = tempOrderRequest.getTempOrderItems().stream()
                .map(tempOrderItems->tempOrderItems.getItemCount())
                .collect(Collectors.toList());

      List<Item> item = itemRepository.findAllByIdIn(itemIds);


        String uuid = UUID.randomUUID().toString();
        Order order = Order.builder()
                .orderStatus(OrderStatus.Try)
                .uuid(uuid)
                .user(principalDetails.getDto())
                .build();
       orderRepository.save(order);

        List<OrderItem> orderItems = new ArrayList<>();
        for(int i =0;i<item.size();i++){
            OrderItem orderItem = OrderItem.builder()
                    .count(itemCounts.get(i))
                    .item(item.get(i))
                    .order(order)
                    .build();
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
    public void afterOrder() throws JsonProcessingException, URISyntaxException {
//        Order order = orderRepository.findById(orderId).orElseThrow();
// api.iamport.kr/users/getToken 아래 바디를 담아 요청을 보내 응답으로 토큰을 받아온다
        //
//        {
//            "imp_key": "",
//            "imp_secret": "",
//        }
        //
        String time = Long.toString(System.currentTimeMillis());

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        TokenRequestDto request = TokenRequestDto.builder()
                .imp_key(imp_key)
                .imp_secret(imp_secret).build();

        //쌓은 바디를 json형태로 반환
        ObjectMapper objectMapper = new ObjectMapper();
        String body = objectMapper.writeValueAsString(request);
        // jsonBody와 헤더 조립
        HttpEntity<String> httpBody = new HttpEntity<>(body, headers);

        RestTemplate restTemplate = new RestTemplate();
        restTemplate.setRequestFactory(new HttpComponentsClientHttpRequestFactory());
        //restTemplate로 post 요청 보내고 오류가 없으면 202코드 반환
        TokenResponseDto response = restTemplate.postForObject(new URI("https://api.iamport.kr/users/getToken"), httpBody, TokenResponseDto.class);
        System.out.println(response.getResponse().toString());



    }

    // 결제하기 버튼 -> 가주문생성 -> 결제방식 선택후 결제 -> 결제 성공or실패 -> 주문상태 변경

}
