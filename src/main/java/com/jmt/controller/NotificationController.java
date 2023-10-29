package com.jmt.controller;

import com.jmt.common.TokenProvidor;
import com.jmt.dto.NotificationDto;
import com.jmt.entity.Notification;
import com.jmt.entity.Member;
import com.jmt.service.EmitterService;
import com.jmt.service.NotificationService;
import com.jmt.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.ArrayList;
import java.util.List;

@RestController
@Slf4j
@RequestMapping("/notification")
@RequiredArgsConstructor
public class NotificationController {
    private final NotificationService notificationService;

    private final MemberService memberService;

    private final TokenProvidor tokenProvidor;

    private final EmitterService emitterService;



    @GetMapping(value = "/sub",produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public SseEmitter subscribe(@AuthenticationPrincipal String userid){
        log.debug("이미터 유저 확인 : " + userid);
        SseEmitter checkEmitter= emitterService.subscribe(userid);
        log.debug("emitter sub chekc : " + checkEmitter);
        return checkEmitter;
    }

    @PostMapping("/send")
    public ResponseEntity<NotificationDto> sendData(
            @AuthenticationPrincipal String userid,
            @RequestBody NotificationDto dto) {
        Notification notification = NotificationDto.toEntity(dto);
        log.debug("MemberTest Userid : " + userid);
        Member member = memberService.getMember(userid);
        notification.setMember(member);
        notificationService.addNotification(notification);

        emitterService.send(userid,dto);

        NotificationDto notificationDto = NotificationDto.toDto(notification);
        return ResponseEntity.ok().body(notificationDto);
    }

    @PutMapping
    public ResponseEntity<NotificationDto> regNotification(@RequestBody NotificationDto dto){
        Notification entity = NotificationDto.toEntity(dto);
//        entity.setMember(memberService.getMember(tokenProvidor.getUserId(token)));
        log.info("entity : " + entity);
        NotificationDto alarmDto = NotificationDto.toDto(entity);
        if(entity == null){
            throw new RuntimeException("엔티티 이즈 널");
        }
        try {
            notificationService.addNotification(entity);
            return ResponseEntity.ok().body(alarmDto);
        }catch (Exception e){
            log.error(e.getMessage());
            return ResponseEntity.badRequest().body(alarmDto);
        }
    }

    @PostMapping
    public ResponseEntity<List<NotificationDto>> readAllNotification(@AuthenticationPrincipal String userid){
        List<Notification> entities = notificationService.showNotification(userid);
        log.debug("===============entities : " + entities);
        List<NotificationDto> entity = new ArrayList<>();

        for(Notification notification : entities){
            entity.add(NotificationDto.toDto(notification));
        }

        System.out.println("entity = " + entity);

        System.out.println("ResponseEntity.ok().body(entity); = " + ResponseEntity.ok().body(entity));
        if(entities == null){
            throw new RuntimeException("엔티티 이즈 널");
        }
        try {
            return ResponseEntity.ok().body(entity);
        }catch (Exception e){
            log.error(e.getMessage());
            return ResponseEntity.badRequest().body(entity);
        }
    }

    @DeleteMapping
    public ResponseEntity<List<NotificationDto>> removeNotification(@RequestBody NotificationDto dto, @AuthenticationPrincipal String userid){
        String tempNotificationId = "8a8ab7938b3cbcaf018b3cbd84990000";
        notificationService.deleteNotification(dto.getId());
        List<Notification> entities = notificationService.showNotification(userid);

        List<NotificationDto> entity = new ArrayList<>();
        for(Notification notification : entities){
            entity.add(NotificationDto.toDto(notification));
        }
        try {
            return ResponseEntity.ok().body(entity);
        }catch (Exception e){
            log.error(e.getMessage());
            return ResponseEntity.badRequest().body(entity);
        }
    }

    @DeleteMapping("/all")
    public ResponseEntity<List<NotificationDto>> removeAllNotification(@AuthenticationPrincipal String userid){
        notificationService.deleteAllNotification(userid);

        List<Notification> entities = notificationService.showNotification(userid);
        List<NotificationDto> entity = new ArrayList<>();

        for(Notification notification : entities){
            entity.add(NotificationDto.toDto(notification));
        }
        if(entities == null){
            throw new RuntimeException("엔티티가 널임");
        }
        try {
            return ResponseEntity.ok().body(entity);
        }catch (Exception e){
            log.error(e.getMessage());
            return ResponseEntity.badRequest().body(entity);
        }
    }
}
