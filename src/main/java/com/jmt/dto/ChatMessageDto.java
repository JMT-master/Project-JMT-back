package com.jmt.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class ChatMessageDto {

    public enum  MessageType{
        ENTER, TALK
    }

    private MessageType type;

    private  String roomId;

    private String sender;

    private String message;

}
