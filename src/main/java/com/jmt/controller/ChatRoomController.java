package com.jmt.controller;

import com.jmt.dto.ChatRoom;
import com.jmt.dto.ResponseDto;
import com.jmt.service.ChatService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/chat")
public class ChatRoomController {

    private final ChatService chatService;

    //채팅 리스트 화면
    @GetMapping("/room")
    public ResponseEntity<?> rooms(){
        try {
            List<ChatRoom> chatRooms =  chatService.findAllRoom();
            ResponseDto<ChatRoom> responseDto = ResponseDto.<ChatRoom>builder()
                    .data(chatRooms)
                    .build();
            return ResponseEntity.ok().body(responseDto);

        }catch (Exception e){
            return null;
        }
    }

    //모든 채팅방 목록 반환
    @GetMapping("/rooms")
    @ResponseBody
    public ResponseEntity<?> room(){
        try {
            List<ChatRoom> chatRooms = chatService.findAllRoom();
            ResponseDto<ChatRoom> responseDto = ResponseDto.<ChatRoom>builder()
                    .data(chatRooms)
                    .build();
            return ResponseEntity.ok().body(responseDto);
        }catch (Exception e){
            return null;
        }
    }

    //채팅방 생성
    @PostMapping("/room")
    @ResponseBody
    public ResponseEntity<?> createRoom(@RequestBody ChatRoom chatRoom){
        try {
               List<ChatRoom> chatRooms = chatService.createRoom(chatRoom.getRoomName());
                ResponseDto<ChatRoom> responseDto = ResponseDto.<ChatRoom>builder()
                        .data(chatRooms)
                        .build();
                return ResponseEntity.ok().body(responseDto);
        }catch (Exception e){

            return null;
        }
    }

    //채팅방 입장 화면
    @GetMapping("/room/enter/{roomId}")
    public String roomDetail(Model model, @PathVariable String roomId){
        model.addAttribute("roomId", roomId);
        return "/chat/roomdetail";
    }

    //특정 채팅방 조회
    @GetMapping("/room/{roomId}")
    @ResponseBody
    public ChatRoom roomInfo(@PathVariable String roomId){
        return chatService.findById(roomId);
    }
}
