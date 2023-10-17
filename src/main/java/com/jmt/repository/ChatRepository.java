package com.jmt.repository;


import com.jmt.dto.ChatRoomDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.util.*;

@Repository
@Slf4j
public class ChatRepository {

    private Map<String, ChatRoomDto> chatRoomDtoMap;

    // 이 메서드는 클래스가 초기화된 후 호출되어 chatRoomDtoMap을 초기화합니다.
    @PostConstruct
    private void init(){
        chatRoomDtoMap = new LinkedHashMap<>(); // LinkedHashMap을 사용하여 순서가 있는 맵을 초기화합니다.
    }

    //전체 채팅방 조회
    public List<ChatRoomDto> findAllRoom(){
        //채팅방 생성 순서를 최신 순으로 반환
        List<ChatRoomDto> chatRooms = new ArrayList<>(chatRoomDtoMap.values());
        Collections.reverse(chatRooms);
        return chatRooms;
    }

    //roomId 기준으로 채팅방 찾기
    public ChatRoomDto findRoomById(String roomId){
        return chatRoomDtoMap.get(roomId);
    }

    //채팅방 만들기
    public ChatRoomDto createChatRoom(String roomName, String roomPwd, int maxUserCnt, boolean secretChk){
        //채팅방 이름이랑 비밀번호로 방 만들어서 리턴하기
        ChatRoomDto chatRoomDto = ChatRoomDto.builder()
                .roomId(UUID.randomUUID().toString())
                .roomName(roomName)
                .roomPwd(roomPwd)
                .secretChk(secretChk)
                .userList(new HashMap<String, String>())
                .maxUserCount(maxUserCnt)
                .build();
        //map에 채팅방 아이디랑 채팅방 자체를 저장하기
        chatRoomDtoMap.put(chatRoomDto.getRoomId(), chatRoomDto);

        return chatRoomDto;
    }

    //채팅방 인원 +1
    public void plusUserCnt(String roomId){
        ChatRoomDto chatRoomDto = chatRoomDtoMap.get(roomId);
        chatRoomDto.setUserCount(chatRoomDto.getUserCount()+1);
    }

    //채팅방 인원 -1
    public void minusUserCnt(String roomId){
        ChatRoomDto chatRoomDto = chatRoomDtoMap.get(roomId);
        chatRoomDto.setUserCount(chatRoomDto.getUserCount()-1);
    }

    //최대 인원수에 따라서 채팅방에 입장 가능 여부 판별
    public boolean checkRoomMaxUserCnt(String roomId){
        ChatRoomDto chatRoomDto = chatRoomDtoMap.get(roomId);
        log.info("현재 인원 확인 [{}, {}]", chatRoomDto.getUserCount(), chatRoomDto.getMaxUserCount());
        //count가 하나 늘었을 때 최대 인원수 넘으면 false
        if (chatRoomDto.getUserCount() + 1 > chatRoomDto.getMaxUserCount()){
            return false;
        }

        return true;
    }

    //채팅방 유저 리스트에 유저를 추가
    public String addUser(String roomId, String userName){
        ChatRoomDto roomDto = chatRoomDtoMap.get(roomId);
        String userUUID = UUID.randomUUID().toString();

        //중복 확인하고 list에 추가하기
        roomDto.getUserList().put(userUUID, userName);

        return userUUID;
    }

    //채팅방에 이미 있는 유저 이름인지 중복 확인
    public String isDuplicateName(String roomId, String userName){
        ChatRoomDto chatRoomDto = chatRoomDtoMap.get(roomId);
        String tmp = userName;
        //userName이 중복이라면 랜덤 숫자를 붙여서 list에 넣고 그래도 중복이면 다시 랜덤 붙이기
        while (chatRoomDto.getUserList().containsValue(tmp)){
            int ranNum = (int)(Math.random()*100) + 1;
            tmp = userName+ranNum;
        }
        return tmp;
    }

    //유저를 리스트에서 삭제
    public void deleteUser(String roomId, String userUUID){
        ChatRoomDto chatRoomDto = chatRoomDtoMap.get(roomId);
        chatRoomDto.getUserList().remove(userUUID);
    }

    //채팅방에서 유저이름을 조회
    public String getUserName(String roomId, String userUUID){
        ChatRoomDto chatRoomDto = chatRoomDtoMap.get(roomId);
        return chatRoomDto.getUserList().get(userUUID);
    }

    //채팅방에 존재하는 전체 유저 리스트 조회
    public ArrayList<String> getUserList(String roomId){
        ArrayList<String> list = new ArrayList<>();

        ChatRoomDto chatRoomDto = chatRoomDtoMap.get(roomId);

        //hasmap을 for문으로 돌리고 value 값만 list에 저장해서 리턴
        chatRoomDto.getUserList().forEach((key, value) -> list.add(value));
        return list;
    }

    // 채팅방 비밀번호 조회
    public boolean confirmPwd(String roomId, String roomPwd){
        return roomPwd.equals(chatRoomDtoMap.get(roomId).getRoomPwd());
    }

    //채팅방 삭제하기
    public void deleteChatRoom(String roomId){
        try {
            //채팅방 삭제
            chatRoomDtoMap.remove(roomId);
            log.info("삭제한 채팅방 : {}", roomId);
        }catch (Exception e){
            System.out.println("에러메세지 = " + e.getMessage());
        }
    }
}
