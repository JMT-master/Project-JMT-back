package com.jmt.controller;

import com.jmt.dto.NoticeDto;
import com.jmt.entity.Notice;
import com.jmt.service.EmitterService;
import com.jmt.service.MemberService;
import com.jmt.service.NoticeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
    private final MemberService memberService;

    @GetMapping
    public ResponseEntity<List<NoticeDto>> readAll() {
        List<Notice> notices = noticeService.readAllNotice();
        List<NoticeDto> noticeDtos = new ArrayList<>();

        notices.forEach((notice) -> {
            log.debug(notice.toString());
            noticeDtos.add(NoticeDto.toDto(notice));
        });
        return ResponseEntity.ok().body(noticeDtos);
    }

    @GetMapping("/{idx}")
    public ResponseEntity<NoticeDto> read(@PathVariable Long idx) {
        log.debug("noticeReadIdx : " + idx);
        NoticeDto noticeDto = NoticeDto.toDto(noticeService.readNotice(noticeService.readNoticeIdx(idx).getNoticeId()));
        return ResponseEntity.ok().body(noticeDto);
    }

    @PostMapping("/admin")
    public ResponseEntity<NoticeDto> writeNotice(@AuthenticationPrincipal String userid, @RequestBody NoticeDto dto) {
        Notice entity = NoticeDto.toEntity(dto);
        log.debug("form에서 받은 dto : " + dto);
        if (entity == null) {
            throw new RuntimeException("엔티티 이즈 널");
        }
        entity.setMember(memberService.getMember(userid));
        NoticeDto noticeDto = NoticeDto.toDto(entity);
        noticeService.createNotice(entity);
        return ResponseEntity.ok().body(noticeDto);
    }

    @PutMapping("/admin")
    public ResponseEntity<NoticeDto> updateNotice(@RequestBody NoticeDto dto) {
        log.debug("updateNotice : " + dto);
        Notice notice = noticeService.updateNotice(dto);
        return ResponseEntity.ok().body(NoticeDto.toDto(notice));
    }

    @DeleteMapping("/admin")
    public ResponseEntity<List<NoticeDto>> deleteNotice(@RequestBody NoticeDto dto) {
        log.debug("Notice Delete idx : " + dto.getIdx());
        Notice targetNotice = noticeService.readNotice(noticeService.readNoticeIdx(dto.getIdx()).getNoticeId());
        log.debug("Notice Delete : " + targetNotice);
        noticeService.deleteNotice(targetNotice);
        List<Notice> noticeList = noticeService.readAllNotice();
        List<NoticeDto> dtos = new ArrayList<>();
        noticeList.forEach(notice -> {
            dtos.add(NoticeDto.toDto(notice));
        });
        return ResponseEntity.ok().body(dtos);

    }
}
