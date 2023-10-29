package com.jmt.controller;

import com.jmt.common.PagingUtil;
import com.jmt.dto.NoticeDto;
import com.jmt.dto.QnaDto;
import com.jmt.dto.ResponseDto;
import com.jmt.entity.Notice;
import com.jmt.service.MemberService;
import com.jmt.service.NoticeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/notice")
@Slf4j
public class NoticeController {

    @Autowired
    NoticeService noticeService;

    @Autowired
    private MemberService memberService;

    @PostMapping("/admin/write")
    public ResponseEntity<?> createNotice(@RequestBody NoticeDto noticeDto,
                                          @AuthenticationPrincipal String userId) {

        try {
            Notice notice = NoticeDto.toEntity(noticeDto);
            log.info("userId : "+userId);
            notice.updateModDate();
            notice.setMember(memberService.getMember(userId));
            List<Notice> noticeList = noticeService.create(notice);
            List<NoticeDto> noticeDtoList = noticeList.stream().map(NoticeDto::new)
                    .collect(Collectors.toList());
            log.info("noticeDtos : {}", noticeDtoList);
            ResponseDto<NoticeDto> responseDto = ResponseDto.<NoticeDto>builder()
                    .data(noticeDtoList)
                    .build();
            return ResponseEntity.ok().body(responseDto);
        }catch (Exception e){
            String error = e.getMessage();
            ResponseDto<String> responseDto = ResponseDto.<String>builder()
                    .error(error)
                    .build();
            return ResponseEntity.badRequest().body(responseDto);
        }
    }

    //일반 유저나 기본적인 전체 list 호출
    @GetMapping
    public ResponseEntity<?> getNoticeList(@RequestParam(defaultValue = "1") int page,
                                           @RequestParam(defaultValue = "10") int size){

        PagingUtil<NoticeDto> noticePaging = noticeService.getNoticeList(page, size);

        return ResponseEntity.ok().body(noticePaging);
    }

    @PostMapping("/admin/{noticeNum}")
    public ResponseEntity<?> updateNotice(@RequestBody NoticeDto noticeDto,
                                          @PathVariable Long noticeNum,
                                          @AuthenticationPrincipal String userId){

        try {
            Notice notice = noticeService.readNoticeByNoticeNum(noticeNum);
            notice.setNoticeCategory(noticeDto.getCategory());
            notice.setNoticeTitle(noticeDto.getTitle());
            notice.setNoticeContent(noticeDto.getContent());
            notice.setMember(memberService.getMember(userId));
            notice.updateModDate();
            log.info("notice : {}", notice);
            List<Notice> noticeList = noticeService.updateNotice(notice);
            List<NoticeDto> noticeDtos = noticeList.stream().map(NoticeDto::new)
                    .collect(Collectors.toList());
            ResponseDto<NoticeDto> responseDto = ResponseDto.<NoticeDto>builder()
                    .data(noticeDtos)
                    .build();
            return ResponseEntity.ok().body(responseDto);
        }catch (Exception e){
            String error = e.getMessage();
            ResponseDto<NoticeDto> responseDto = ResponseDto.<NoticeDto>builder()
                    .error(error)
                    .build();
            return ResponseEntity.badRequest().body(responseDto);
        }

    }


    @DeleteMapping("/admin")
    public ResponseEntity<?> deleteNotice(@AuthenticationPrincipal String userId,
                                          @RequestBody Long noticeNum){
        try {
            Notice notice = noticeService.readNoticeByNoticeNum(noticeNum);
            notice.setMember(memberService.getMember(userId));
            List<Notice> noticeList = noticeService.deleteNotice(notice);
            List<NoticeDto> noticeDtos = noticeList.stream().map(NoticeDto::new)
                    .collect(Collectors.toList());
            ResponseDto<NoticeDto> responseDto = ResponseDto.<NoticeDto>builder()
                    .data(noticeDtos)
                    .build();
            return ResponseEntity.ok().body(responseDto);
        }catch (Exception e){
            String error = e.getMessage();
            ResponseDto<NoticeDto> responseDto = ResponseDto.<NoticeDto>builder()
                    .error(error)
                    .build();
            return ResponseEntity.badRequest().body(responseDto);
        }
    }

    @GetMapping("{id}")
    public ResponseEntity<?> readByNoticeNum(@PathVariable Long id){

        try {
            List<Notice> noticeList = noticeService.readByNoticeNum(id);
            log.info("notices : {}", noticeList);
            List<NoticeDto> noticeDtos = noticeList.stream().map(NoticeDto::new)
                    .collect(Collectors.toList());
            ResponseDto<NoticeDto> responseDto = ResponseDto.<NoticeDto>builder()
                    .data(noticeDtos)
                    .build();
            return ResponseEntity.ok().body(responseDto);
        }catch (Exception e){
            String error = e.getMessage();
            ResponseDto<NoticeDto> responseDto = ResponseDto.<NoticeDto>builder()
                    .error(error)
                    .build();
            return ResponseEntity.badRequest().body(responseDto);
        }
    }

    //업데이트할 data를 가져오는 mapping
    @GetMapping("/admin/{id}")
    public ResponseEntity<?> readForUpdate(@PathVariable Long id,
                                           @AuthenticationPrincipal String userId){
        try {
            List<Notice> noticeList = noticeService.readByNoticeNum(id);
            log.info("notices : {}", noticeList);
            List<NoticeDto> noticeDtos = noticeList.stream().map(NoticeDto::new)
                    .collect(Collectors.toList());
            ResponseDto<NoticeDto> responseDto = ResponseDto.<NoticeDto>builder()
                    .data(noticeDtos)
                    .build();
            return ResponseEntity.ok().body(responseDto);
        }catch (Exception e){
            String error = e.getMessage();
            ResponseDto<NoticeDto> responseDto = ResponseDto.<NoticeDto>builder()
                    .error(error)
                    .build();
            return ResponseEntity.badRequest().body(responseDto);
        }
    }
//    @PostMapping("/write")
//    public ResponseEntity<Notice> writeNotice(@RequestBody NoticeDto dto){
//        Notice entity = NoticeDto.toEntity(dto);
//        if(entity == null){
//            throw new RuntimeException("엔티티 이즈 널");
//        }
//        entity.setNoticeId(null);
//        try{
//            noticeService.createNotice(entity);
//            return ResponseEntity.ok().body(entity);
//        }catch (Exception e){
//            log.error(e.getMessage());
//            return ResponseEntity.badRequest().body(entity);
//        }
//    }


}
