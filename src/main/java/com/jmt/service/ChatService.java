package com.jmt.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jmt.dto.ChatRoomDto;
import com.jmt.entity.ChatRoom;
import com.jmt.repository.ChatMessageRepository;
import com.jmt.repository.ChatRoomRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.*;

@Slf4j
@RequiredArgsConstructor
@Service
@Transactional
public class ChatService {

    private final ChatRoomRepository chatRoomRepository;

    private final ChatMessageRepository chatMessageRepository;

    //시작할 때 처음으로 읽어올 read

    public List<ChatRoom> readRoom(){
       log.info("서비스에서 리스트" + chatRoomRepository.findAll());
        return chatRoomRepository.findAll();
    }

    //만들고 list를 다시 뿌려주기
    public List<ChatRoom> createRoom(ChatRoom chatRoom){
        chatRoomRepository.save(chatRoom);
        return chatRoomRepository.findAll();
    }


}