package com.jmt.controller;

import com.jmt.dto.ChatRoomDto;
import com.jmt.dto.QnaDto;
import com.jmt.dto.ResponseDto;
import com.jmt.entity.ChatRoom;
import com.jmt.service.ChatService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@Slf4j
@RequestMapping("/chat")
public class ChatRoomController {

    @Autowired
    private ChatService chatService;

    @GetMapping("/room")
    public ResponseEntity<?> readRooms(){

        try {
            List<ChatRoom> chatRooms = chatService.readRoom();
                log.info("chat rooms : " + chatRooms);
            List<ChatRoomDto> roomDtos = chatRooms.stream().map(ChatRoomDto::new)
                    .collect(Collectors.toList());
            log.info("chat dtos" + roomDtos);
            ResponseDto<ChatRoomDto> responseDto = ResponseDto.<ChatRoomDto>builder()
                    .data(roomDtos)
                    .build();
            log.info("response dto : "+responseDto);
            return ResponseEntity.ok().body(responseDto);
        }catch (Exception e){
            String error = e.getMessage();
            System.out.println("error = " + error);
            ResponseDto<ChatRoomDto> responseDto = ResponseDto.<ChatRoomDto>builder()
                    .error(error)
                    .build();
            return ResponseEntity.badRequest().body(responseDto);
        }

    }

    @PostMapping("/room")
    public ResponseEntity<?> createRoom(@RequestBody ChatRoomDto chatRoomDto){
        try {
            ChatRoom chatRoom = ChatRoomDto.toEntity(chatRoomDto);
            chatRoom.setRoomName(chatRoomDto.getRoomName());
            List<ChatRoom> chatRooms = chatService.createRoom(chatRoom);
            List<ChatRoomDto> roomDtos = chatRooms.stream().map(ChatRoomDto::new)
                    .collect(Collectors.toList());
            ResponseDto<ChatRoomDto> responseDto = ResponseDto.<ChatRoomDto>builder()
                    .data(roomDtos)
                    .build();
            return ResponseEntity.ok().body(responseDto);
        }catch (Exception e){
            String error = e.getMessage();
            System.out.println("error = " + error);
            ResponseDto<ChatRoomDto> responseDto = ResponseDto.<ChatRoomDto>builder()
                    .error(error)
                    .build();
            return ResponseEntity.badRequest().body(responseDto);
        }
    }
}
