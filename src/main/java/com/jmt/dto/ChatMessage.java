package com.jmt.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class ChatMessage {

    public enum  MessageType{
        ENTER, TALK, JOIN
    }

    private MessageType type;
    //채팅방  ID
    private  String roomId;
    //보내는 사람
    private String sender;
    // 내용
    private String message;

}
