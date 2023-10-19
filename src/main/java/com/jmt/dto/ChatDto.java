package com.jmt.dto;

import com.jmt.constant.MessageType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ChatDto {
    
    //메시지 형식 DTO
    //채널 구분 식별자
    private Integer channelId;
    
    private Integer writerId;
    
    private String chat;

}
