package com.jmt.service;

import com.jmt.dto.ChatRoomDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.*;

@Service
@Slf4j
@RequiredArgsConstructor
public class ChatService {

    private Map<String, ChatRoomDto> chatRooms;

    @PostConstruct
    //의존관계 주입 완료 되면 실행 되는 코드
    private void init(){

        chatRooms = new LinkedHashMap<>();
    }

    //채팅방 불러오기
    public List<ChatRoomDto> findAllRoom(){
        //채팅방 최근 생성 순으로 반환
        List<ChatRoomDto> result = new ArrayList<>(chatRooms.values());
        Collections.reverse(result);

        return result;
    }

    //채팅방 하나 불러오기
    public ChatRoomDto findById(String roomId){

        return chatRooms.get(roomId);
    }

    //채팅방 생성
    public List<ChatRoomDto> createRoom(String name){
        ChatRoomDto chatRoomDto = ChatRoomDto.create(name);
        chatRooms.put(chatRoomDto.getRoomId(), chatRoomDto);
        return (List<ChatRoomDto>) chatRooms;
    }
}
