package com.jmt.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.*;

@Configuration
@EnableWebSocketMessageBroker
public class ChatConfig implements WebSocketMessageBrokerConfigurer {

    //웹 소켓 연결을 위한 엔드포인트 설정 및 stomp의 sub/pub 엔드포인트 설정

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {

        //stomp 접속 주소 url => /ws-stomp로 설정할 것입
        registry.addEndpoint("/ws-stomp") //연결될 엔드포인트
                .withSockJS(); //sockjs 연결할 것
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        //메세지를 구독하는 요청 url => 메시지를 받을 때
        registry.enableSimpleBroker("/sub");

        //메시지를 발행하는 요청 url => 즉 메시지를 보낼 때
        registry.setApplicationDestinationPrefixes("/pub");

    }
}
