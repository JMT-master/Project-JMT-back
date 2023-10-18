package com.jmt.dto;

import com.jmt.entity.ChatUserEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ChatUserDto {

    //Chat User Entity를 보고 작성

    // DB에 저장 되는 chat id;
    private Long chatId;
    //소셜에서 제공 받은 유저 닉네임
    private String nickName;
    //제공받은 이메일
    private String email;

    // chat user entity를 to dto
    public static ChatUserDto of(ChatUserEntity chatUser){
        ChatUserDto chatUserDto = ChatUserDto.builder()
                .chatId(chatUser.getChatId())
                .nickName(chatUser.getNickName())
                .email(chatUser.getEmail())
                .build();
        return chatUserDto;
    }

}
