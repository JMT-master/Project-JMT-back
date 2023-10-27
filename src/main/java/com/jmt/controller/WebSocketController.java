//package com.jmt.controller;
//
//import com.jmt.dto.ChatDto;
//import lombok.RequiredArgsConstructor;
//import org.springframework.messaging.handler.annotation.MessageMapping;
//import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
//import org.springframework.messaging.simp.SimpMessagingTemplate;
//import org.springframework.web.bind.annotation.RestController;
//
//@RestController
//@RequiredArgsConstructor
//public class WebSocketController {
//
//    private final SimpMessagingTemplate simpMessagingTemplate;
//
//    @MessageMapping("/chat/message")
//    public void sendMessage(ChatMessageDto chatDto, SimpMessageHeaderAccessor accessor){
//        simpMessagingTemplate.convertAndSend("/topic/chat/room/" + chatDto.getRoomId(), chatDto);
//    }
//
//}
