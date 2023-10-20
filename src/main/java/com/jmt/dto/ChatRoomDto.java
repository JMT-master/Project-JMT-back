package com.jmt.dto;

import com.jmt.entity.ChatMessage;
import com.jmt.entity.ChatRoom;
import com.jmt.service.ChatService;
import lombok.*;
import org.springframework.web.socket.WebSocketSession;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChatRoomDto {

    private String roomId;
    private String roomName;

    public ChatRoomDto(final ChatRoom chatRoom){
        this.roomId = chatRoom.getRoomId();
        this.roomName = chatRoom.getRoomName();
    }

    public static ChatRoom toEntity(final ChatRoomDto chatRoomDto){
        return ChatRoom.builder()
                .roomId(UUID.randomUUID().toString())
                .roomName(chatRoomDto.getRoomName())
                .build();
    }
}
