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

import java.io.IOException;
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
    public List<Notification> showNotification(String email){
        log.info("NotificationTest :" + repository.findNotificationsByMember_Email(email));
        return repository.findNotificationsByMember_Email(email);
    }

    @Transactional
    public Notification getNotification(String notifyId){
        return repository.findByNotificationId(notifyId);
    }

    @Transactional
    public void deleteNotification(String notifyId){
        repository.delete(getNotification(notifyId));
    }

    @Transactional
    public void deleteAllNotification(String userid){ repository.deleteAll(repository.findNotificationsByMember_Email(userid));}
}
