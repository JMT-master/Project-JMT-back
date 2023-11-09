package com.jmt.controller;

import com.jmt.dto.ChatMessageDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
public class MessageController {

    private final SimpMessageSendingOperations sendingOperations;

    private final SimpMessagingTemplate messagingTemplate;

    @MessageMapping("/chat/message/{roomId}")
    public void enter(@Payload ChatMessageDto message, @DestinationVariable("roomId") String roomId){
        if (ChatMessageDto.MessageType.ENTER.equals(message.getType())){
//            message.setMessage(message.getSender()+"님이 입장하였습니다.");
        }
        sendingOperations.convertAndSend("/topic/chat/room/" + roomId, message);

    }

    public void sendMessageToSubscribers(String roomId, String message){
        messagingTemplate.convertAndSend("/topic/chat/room/"+roomId, message);
    }
}
