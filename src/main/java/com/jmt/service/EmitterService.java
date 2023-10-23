package com.jmt.service;

import com.jmt.entity.Notification;
import com.jmt.repository.EmitterRepository;
import com.jmt.repository.NotificationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import static com.jmt.common.ExpiredTime.EXPIRED_TIMEOUT;

@Service
@Slf4j
@RequiredArgsConstructor
public class EmitterService {

    private final EmitterRepository emitterRepository;
    private final NotificationRepository notificationRepository;

    //    -이미터-
    /*
    @param userid = 구독할 클라이언트의 아이디
    @return SseEmitter = 서버에서 보내는 이벤트 Emitter
     */
    public SseEmitter subscribe(String userid){
        //이미터 생성
        SseEmitter emitter = createEmitter(userid);
        //생성 후, 유저에게 이미터 생성 알림
        List<Notification> notifications = notificationRepository.findNotificationsByMember_Userid(userid);
        notifications.forEach(notify->{
            log.debug("notifyChk" + notify);
            if(notify != null) {
                //ToDo : 보낼때 Notification형태로 보내지지 않음 혹은 프론트에서 받지 못함 . 고쳐야함
                sendToClient(userid, "message",notify);
            }else{
                log.debug("notifyChk" + notify);
//                sendToClient(userid, "User = " + userid + "의 이미터 생성됨");
            }
        });

//        log.debug("만들어진 emitter 확인  : " + emitter);
        return emitter;
    }

    /*
    @param userid = 유저 아이디
    @return SseEmitter 생성된 이미터

    사용을 위해 만든 메소드, 다른 서비스 로직에서 sendToClient 메소드를 이용해 유저에게 데이터를 전송
     */
    public void send(@AuthenticationPrincipal String userid, String eventType, Notification entity) {
        Notification notification = notificationRepository.save(entity);
        Map<String, SseEmitter> sseEmitters = emitterRepository.findAllEmitterByMemberId(userid);

        sseEmitters.forEach(
                (key, emitter)->{
                    emitterRepository.save(key, emitter);
                    sendToClient(key,eventType, notification);
                }
        );

    }

    /*
    @param userid = 유저 아이디
    @return SseEmitter 생성된 이미터

    사용자 아이디를 기반으로 이미터를 생성함.
    */
    private SseEmitter createEmitter(String userid) {
        SseEmitter emitter = new SseEmitter(EXPIRED_TIMEOUT);

        emitterRepository.save(userid, emitter);
        log.debug("createEmitter의 이미터 : " + emitter);
        // Emitter가 만료될 때(모든 데이터가 성공적으로 전송된 상태) Emitter를 삭제한다.
        emitter.onCompletion(() -> emitterRepository.deleteById(userid));
        // Emitter가 타임아웃 되었을 때(지정된 시간동안 어떠한 이벤트도 전송되지 않았을 때) Emitter를 삭제한다.
        emitter.onTimeout(() -> emitterRepository.deleteById(userid));
        sendToClient(userid,"message","Emitter Created. Userid = " + userid);
        return emitter;
    }

    /*
    @param userid = 데이터를 받을 유저 아이디
    @param data = 전송할 데이터

     */
    private void sendToClient(String userid, String eventType, Object data) {
        //notificationdto를 string으로 바꿔서 data로 보내기, 프론트에서 다시 JSon으로
        SseEmitter emitter = emitterRepository.get(userid);
        if (emitter != null) {
            try {
                emitter.send(SseEmitter.event()
                        .id(userid)
                        .name(eventType)
                        .data(data));
            } catch (IOException e) {
                emitterRepository.deleteById(userid);
                emitter.completeWithError(e);
            }
        }
    }
}
