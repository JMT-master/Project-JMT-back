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
    //메시지는 두가지의 종류가 있다. : 입장했을 때, 채팅 했을 때
    //메시지 타입에 따라서 동작하는 구조를 다르게 해야함
    //입장과 퇴장 Enter랑 Leave의 경우 각각 이벤트가 처리되어야하고
    //talk는 해딩 채팅방을 구독하는 모든 사용자에게 전달되어야함

    //enum 호출해서 사용
    private MessageType type;
    //방 번호
    private String roomId;
    //채팅을 보낸 사람
    private String sender;
    //채팅
    private String message;
    //채팅 발송한 시간
    private String time;

//    //파일을 업로드 한다면 사용할 변수
//    //근데 파일 부분은 아마 뺄듯
//    private String dataUrl;
//    //파일 이름
//    private String fileName;
//    //파일 경로
//    private String fileDir;

}
