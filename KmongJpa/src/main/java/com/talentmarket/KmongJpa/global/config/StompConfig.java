package com.talentmarket.KmongJpa.global.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.server.support.HttpSessionHandshakeInterceptor;

@Configuration
@RequiredArgsConstructor
@EnableWebSocketMessageBroker
public class StompConfig implements WebSocketMessageBrokerConfigurer {

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        //클라이언트에서 보낸 메세지를 받을 prefix
        //클라이언트에서 send 요청을 처리하는 endpoint 설정
        registry.setApplicationDestinationPrefixes("/chat/send");

        //해당 주소를 구독하고 있는 클라이언트 들에게 메세지 전달
        registry.enableSimpleBroker("/chat/receive");

    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/chat/stomp-chatting") //SockJS 연결주소
                .addInterceptors(new HttpSessionHandshakeInterceptor())
                .setAllowedOriginPatterns("*");
//                .withSockJS();
        // 주ㅡ소 : ws://localhost:8080/ws-stomp
    }
}
