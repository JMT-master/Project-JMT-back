package com.jmt.service;

import com.jmt.dto.ChatRoomDto;
import com.jmt.entity.ChatRoom;
import com.jmt.repository.ChatRoomRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Slf4j
@RequiredArgsConstructor
@Service
@Transactional
public class ChatService {

    private final ChatRoomRepository chatRoomRepository;

    //시작할 때 처음으로 읽어올 read

    public List<ChatRoom> readRoom(){
        return chatRoomRepository.findAll();
    }

    //만들고 list를 다시 뿌려주기
    public List<ChatRoom> createRoom(ChatRoom chatRoom){
        chatRoomRepository.save(chatRoom);
        return chatRoomRepository.findAll();
    }

    //채팅방 삭제하기
    public List<ChatRoom> deleteRoom(ChatRoomDto chatRoomDto){
        ChatRoom chatRoom = chatRoomRepository.findByRoomId(chatRoomDto.getRoomId());
        chatRoomRepository.delete(chatRoom);
        return chatRoomRepository.findAll();
    }
}