package com.jmt.controller;

import com.jmt.dto.NoticeDto;
import com.jmt.dto.NoticeSendDto;
import com.jmt.dto.ResponseDto;
import com.jmt.entity.Notice;
import com.jmt.service.EmitterService;
import com.jmt.service.MemberService;
import com.jmt.service.NoticeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@RestController
@RequestMapping("/notice")
@RequiredArgsConstructor
@Slf4j
public class NoticeController {

    private final NoticeService noticeService;
    private final EmitterService emitterService;
    private final MemberService memberService;

//    @GetMapping
//    public ResponseEntity<List<NoticeDto>> readAll() {
//        List<Notice> notices = noticeService.readAllNotice();
//        List<NoticeDto> noticeDtos = new ArrayList<>();
//
//        notices.forEach((notice) -> {
//            log.debug(notice.toString());
//            noticeDtos.add(NoticeDto.toDto(notice));
//        });
//        return ResponseEntity.ok().body(noticeDtos);
//    }

    @GetMapping
    public ResponseEntity<Page<NoticeDto>> readAllPageable(Pageable pageable) {
        Page<NoticeDto> noticeDtos = noticeService.readAllNotice(pageable);

//        notices.forEach((notice) -> {
//            log.debug(notice.toString());
//            noticeDtos.add(NoticeDto.toDto(notice));
//        });
        return ResponseEntity.ok().body(noticeDtos);
    }

    @GetMapping("/search")
    public ResponseEntity<Page<NoticeDto>> searchReadKnowledge(@RequestParam(value = "select") String select,
                                                           @RequestParam(value = "result") String result,
                                                           Pageable pageable) {
        Page<NoticeDto> search = noticeService.search(select, result, pageable);

        return ResponseEntity.ok().body(search);
    }


    @GetMapping("/{idx}")
    public ResponseEntity<List<NoticeSendDto>> read(@PathVariable Long idx) {
        log.debug("noticeReadIdx : " + idx);
        List<NoticeSendDto> noticeDto = noticeService.readNoticeIdx(idx);

        return ResponseEntity.ok().body(noticeDto);
    }

    @PostMapping("/admin")
    public ResponseEntity<NoticeDto> writeNotice(
            @AuthenticationPrincipal String userid,
            @RequestPart(value = "file", required = false) List<MultipartFile> multipartFiles,
            @RequestPart(value = "data") NoticeDto dto) {
        if (dto == null) {
            throw new RuntimeException("엔티티 이즈 널");
        }
        NoticeDto noticedto = noticeService.createNotice(multipartFiles, dto, userid);
        return ResponseEntity.ok().body(noticedto);
    }

    @PutMapping("/admin")
    public ResponseEntity<NoticeDto> updateNotice(
            @AuthenticationPrincipal String email,
            @RequestBody NoticeDto dto) {
        Notice notice = noticeService.updateNotice(dto);
        return ResponseEntity.ok().body(NoticeDto.toDto(notice));
    }

    @DeleteMapping("/admin")
    public ResponseEntity<Page<NoticeDto>> deleteNotice(@RequestBody NoticeSendDto dto, Pageable pageable) {
        noticeService.deleteNotice(dto);
        Page<NoticeDto> noticedtos = noticeService.readAllNotice(pageable);
        return ResponseEntity.ok().body(noticedtos);

    }

    @PostMapping("/viewFile")
    public ResponseEntity<Resource> showFileImage(@RequestBody NoticeSendDto noticeSendDto) throws IOException {
        System.out.println("noticeSendDto = " + noticeSendDto);
        Path path = Paths.get(noticeSendDto.getServerPath());

        String contentType = Files.probeContentType(path);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentDisposition(
                ContentDisposition.builder("inline")
                        .filename(noticeSendDto.getOriginalName(), StandardCharsets.UTF_8).build() // StandardCharsets.UTF_8 : UTF-8로 인코딩
        );

        headers.add(HttpHeaders.CONTENT_TYPE, contentType);

        Resource resource = new InputStreamResource(Files.newInputStream(path));

        return new ResponseEntity<>(resource, headers, HttpStatus.OK);
    }
}
