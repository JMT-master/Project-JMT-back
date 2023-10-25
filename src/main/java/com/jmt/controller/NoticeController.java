package com.jmt.controller;

import com.jmt.dto.LoginDto;
import com.jmt.dto.NoticeDto;
import com.jmt.entity.Notice;
import com.jmt.service.EmitterService;
import com.jmt.service.NoticeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/notice")
@RequiredArgsConstructor
@Slf4j
public class NoticeController {

    private final NoticeService noticeService;
    private final EmitterService emitterService;

    @GetMapping
    public ResponseEntity<List<NoticeDto>> readAll(){
        List<Notice> notices = noticeService.readAllNotice();
        List<NoticeDto> noticeDtos = new ArrayList<>();

        notices.forEach((notice -> {
            noticeDtos.add(NoticeDto.toDto(notice));
        }));
        return ResponseEntity.ok().body(noticeDtos);
    }

    @GetMapping("/read")
    public ResponseEntity<NoticeDto> read(Long idx){
        NoticeDto noticeDto = NoticeDto.toDto(noticeService.readNotice(idx));
        return ResponseEntity.ok().body(noticeDto);
    }
    @PostMapping("/write")
    public ResponseEntity<Notice> writeNotice(@AuthenticationPrincipal String userid, @RequestBody NoticeDto dto) {
        Notice entity = NoticeDto.toEntity(dto);
        if (entity == null) {
            throw new RuntimeException("엔티티 이즈 널");
        }
        entity.setNoticeId(null);
        try {
            noticeService.createNotice(entity);
            emitterService.send(userid,dto);
            return ResponseEntity.ok().body(entity);
        } catch (Exception e) {
            log.error(e.getMessage());
            return ResponseEntity.badRequest().body(entity);
        }
    }

    @PostMapping("/update")
    public ResponseEntity<Notice> updataNotice(Long idx, @RequestBody NoticeDto dto){
        Notice notice = noticeService.readNotice(idx);
        notice.setNoticeCategory(dto.getCategory());
        notice.setNoticeTitle(dto.getTitle());
        notice.setNoticeContent(dto.getTitle());
        return ResponseEntity.ok().body(notice);
    }

    @PostMapping("/delete")
    public ResponseEntity<List<NoticeDto>> deleteNotice(long idx){
        Notice targetNotice = noticeService.readNotice(idx);
        noticeService.deleteNotice(targetNotice);
        return readAll();

    }


}
