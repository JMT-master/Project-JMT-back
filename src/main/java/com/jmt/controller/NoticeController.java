package com.jmt.controller;

import com.jmt.dto.NoticeDto;
import com.jmt.entity.Notice;
import com.jmt.service.NoticeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/notice")
@Slf4j
public class NoticeController {

    @Autowired
    NoticeService noticeService;

    @PostMapping("/write")
    public ResponseEntity<Notice> writeNotice(@RequestBody NoticeDto dto){
        Notice entity = NoticeDto.toEntity(dto);
        if(entity == null){
            throw new RuntimeException("엔티티 이즈 널");
        }
        entity.setNoticeId(null);
        try{
            noticeService.createNotice(entity);
            return ResponseEntity.ok().body(entity);
        }catch (Exception e){
            log.error(e.getMessage());
            return ResponseEntity.badRequest().body(entity);
        }

    }
}
