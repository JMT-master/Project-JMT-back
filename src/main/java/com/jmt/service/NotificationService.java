package com.jmt.service;


import com.jmt.entity.Notification;
import com.jmt.repository.EmitterRepository;
import com.jmt.repository.NotificationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.List;

import static com.jmt.common.ExpiredTime.EXPIRED_TIMEOUT;

@Service
@Slf4j
@RequiredArgsConstructor
public class NotificationService {
    private final NotificationRepository repository;
    private final EmitterRepository emitterRepository;

    @Transactional
    public void addNotification(Notification entity){
        repository.save(entity);
    }

    @Transactional
    public List<Notification> showNotification(String userid){
        log.info("NotificationTest :" + repository.findNotificationsByMember_Userid(userid));
        return repository.findNotificationsByMember_Userid(userid);
    }

    @Transactional
    public Notification getNotification(String alarmId){
        return repository.findByNotificationId(alarmId);
    }

    @Transactional
    public void deleteNotification(String alarmId){
        repository.delete(getNotification(alarmId));
    }

//    -이미터-

    public SseEmitter createEmitter(String userid){
        SseEmitter emitter = new SseEmitter(EXPIRED_TIMEOUT);
        emitterRepository.save(userid, emitter);
        // Emitter가 완료될 때(모든 데이터가 성공적으로 전송된 상태) Emitter를 삭제한다.
        emitter.onCompletion(() -> emitterRepository.deleteById(userid));
        // Emitter가 타임아웃 되었을 때(지정된 시간동안 어떠한 이벤트도 전송되지 않았을 때) Emitter를 삭제한다.
        emitter.onTimeout(() -> emitterRepository.deleteById(userid));

        return emitter;
    }
}
