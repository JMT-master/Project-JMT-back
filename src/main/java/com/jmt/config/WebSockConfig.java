package com.jmt.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.*;
@RequiredArgsConstructor
@Configuration
@EnableWebSocketMessageBroker
public class WebSockConfig implements WebSocketMessageBrokerConfigurer {
    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/ws/chat")
                .setAllowedOrigins("*");
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        //서버에서 클라이언트로 보내주는 메소드
        registry.enableSimpleBroker("/topic");
        //클라이언트가 서버로 보낼때 앞에 붙어야 하는거
        registry.setApplicationDestinationPrefixes("/app");
    }

}