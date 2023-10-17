package com.jmt.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChatRoomDto {
    //현재 채팅하고 있는 채팅방의 고유 아이디
    private String roomId;
    //채팅방의 이름
    private String roomName;
    //채팅방의 인원수
    private int userCount;
    //채팅방 인원 제한을 위한 변수
    private int maxUserCount;
    //채팅방을 삭제하려면 필요한 pwd
    private String roomPwd;
    //채팅방의 잠금 여부
    private boolean secretChk;

    private HashMap<String, String> userList;


}
