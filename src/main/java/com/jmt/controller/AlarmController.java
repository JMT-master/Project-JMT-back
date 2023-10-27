package com.jmt.controller;

import antlr.Token;
import com.jmt.common.TokenProvidor;
import com.jmt.dto.AlarmDto;
import com.jmt.dto.MemberDto;
import com.jmt.entity.Alarm;
import com.jmt.entity.Member;
import com.jmt.repository.AlarmRepository;
import com.jmt.service.AlarmService;
import com.jmt.service.MemberService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@RestController
@Slf4j
@RequestMapping("/alarm")
public class AlarmController {

    @Autowired
    AlarmService alarmService;
    @Autowired
    MemberService memberService;

    @Autowired
    TokenProvidor tokenProvidor;

    @PutMapping
    public ResponseEntity<AlarmDto> regAlarm(@RequestBody AlarmDto dto, HttpServletRequest request){
        System.out.println("request = " + request);
        String token = tokenProvidor.parseJwt(request);
        System.out.println("token = " + token);
        Alarm entity = AlarmDto.toEntity(dto);
        entity.setMember(memberService.getMember(tokenProvidor.getUserId(token)));
        log.info("entity : " + entity);
        AlarmDto alarmDto = AlarmDto.toDto(entity);
        if(entity == null){
            throw new RuntimeException("엔티티 이즈 널");
        }
        try {
            alarmService.addAlarm(entity);
            return ResponseEntity.ok().body(alarmDto    );
        }catch (Exception e){
            log.error(e.getMessage());
            return ResponseEntity.badRequest().body(alarmDto);
        }
    }

    @PostMapping
    public ResponseEntity<List<AlarmDto>> requestAlarm(@RequestBody MemberDto dto){
        List<Alarm> entities = alarmService.showAlarm(memberService.getMember(dto.getEmail()));

        List<AlarmDto> entity = new ArrayList<>();

        for(Alarm alarm : entities){
            entity.add(AlarmDto.toDto(alarm));
        }

        System.out.println("entity = " + entity);
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
    public ResponseEntity<List<AlarmDto>> removeAlarm(String alarmId){
        String tempAlarmId = "8a8ab7938b3cbcaf018b3cbd84990000";
        Member member = memberService.getMember("이장");
        alarmService.deleteAlarm(tempAlarmId);
        List<Alarm> entities = alarmService.showAlarm(memberService.getMember(member.getUsername()));

        List<AlarmDto> entity = new ArrayList<>();
        for(Alarm alarm : entities){
            entity.add(AlarmDto.toDto(alarm));
        }
        try {
            return ResponseEntity.ok().body(entity);
        }catch (Exception e){
            log.error(e.getMessage());
            return ResponseEntity.badRequest().body(entity);
        }
    }
}
