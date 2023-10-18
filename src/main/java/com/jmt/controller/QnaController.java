package com.jmt.controller;

import com.jmt.dto.QnaDto;
import com.jmt.dto.ResponseDto;
import com.jmt.entity.QnaEntity;
import com.jmt.service.MemberService;
import com.jmt.service.QnaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/qna")
public class QnaController {

    @Autowired
    private QnaService qnaService;
    @Autowired
    private MemberService memberService;

    @PostMapping("/write")
    public ResponseEntity<?> createQna(@RequestBody QnaDto qnaDto,
                                       @AuthenticationPrincipal String userid){

        try {
            QnaEntity qnaEntity = QnaDto.toEntity(qnaDto);
            qnaEntity.setId(null);
            qnaEntity.setMember(memberService.getMember(userid));

            List<QnaEntity> qnaEntities = qnaService.create(qnaEntity);
            List<QnaDto> qnaDtos = qnaEntities.stream().map(QnaDto::new)
                    .collect(Collectors.toList());
            ResponseDto<QnaDto> response = ResponseDto.<QnaDto>builder()
                    .data(qnaDtos)
                    .build();
            return ResponseEntity.ok().body(response);
        }catch (Exception e){
            String error = e.getMessage();
            ResponseDto<QnaDto> responseDto = ResponseDto.<QnaDto>builder()
                    .error(error)
                    .build();
            return ResponseEntity.badRequest().body(responseDto);
        }

    }

    @GetMapping
    public ResponseEntity<?> readQna(@AuthenticationPrincipal String userId){
        try {
//            List<QnaEntity> qnaEntities = qnaService.readByUserId(userId);
            List<QnaEntity> qnaEntities = qnaService.read();
            List<QnaDto> qnaDtos = qnaEntities.stream().map(QnaDto::new)
                    .collect(Collectors.toList());
            ResponseDto<QnaDto> responseDto = ResponseDto.<QnaDto>builder()
                    .data(qnaDtos)
                    .build();
            return ResponseEntity.ok().body(responseDto);
        }catch (Exception e){
            String error = e.getMessage();
            System.out.println("error = " + error);
            ResponseDto<QnaDto> responseDto = ResponseDto.<QnaDto>builder()
                    .error(error)
                    .build();
            return ResponseEntity.badRequest().body(responseDto);
        }
    }

    @PutMapping
    public ResponseEntity<?> updateQna(@RequestBody QnaDto qnaDto,
                                       @AuthenticationPrincipal String userId){
        try {
            QnaEntity qnaEntity = QnaDto.toEntity(qnaDto);
            qnaEntity.setMember(memberService.getMember(userId));

            List<QnaEntity> qnaEntities = qnaService.update(qnaEntity);
            List<QnaDto> qnaDtos = qnaEntities.stream().map(QnaDto::new).collect(Collectors.toList());
            ResponseDto<QnaDto> responseDto = ResponseDto.<QnaDto>builder()
                    .data(qnaDtos)
                    .build();
            return ResponseEntity.ok().body(responseDto);
        }catch (Exception e){
            String error = e.getMessage();
            ResponseDto<QnaDto> responseDto = ResponseDto.<QnaDto>builder()
                    .error(error)
                    .build();
            return ResponseEntity.badRequest().body(responseDto);
        }
    }

    @DeleteMapping
    public ResponseEntity<?> deleteQna(@AuthenticationPrincipal String userId,
                                       @RequestBody QnaDto qnaDto){
        try {
            QnaEntity qnaEntity = QnaDto.toEntity(qnaDto);
            qnaEntity.setMember(memberService.getMember(userId));
            List<QnaEntity> qnaEntities = qnaService.delete(qnaEntity);
            List<QnaDto> qnaDtos = qnaEntities.stream().map(QnaDto::new)
                    .collect(Collectors.toList());
            ResponseDto<QnaDto> responseDto = ResponseDto.<QnaDto>builder()
                    .data(qnaDtos)
                    .build();
            return ResponseEntity.ok().body(responseDto);
        }catch (Exception e){
            String error = e.getMessage();
            ResponseDto<QnaDto> responseDto = ResponseDto.<QnaDto>builder()
                    .error(error)
                    .build();
            return ResponseEntity.badRequest().body(responseDto);
        }
    }
}
