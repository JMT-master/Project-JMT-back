package com.jmt.controller;

import com.jmt.dto.QnaDto;
import com.jmt.dto.ResponseDto;
import com.jmt.entity.Qna;
import com.jmt.service.MemberService;
import com.jmt.service.QnaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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
                                       String userid){

        try {
            Qna qna = QnaDto.toEntity(qnaDto);
            qna.setId(null);
            qna.setMember(memberService.getMember(userid));

            List<Qna> qnaEntities = qnaService.create(qna);
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
    public ResponseEntity<?> readQna( String userId){
        try {
//            List<QnaEntity> qnaEntities = qnaService.readByUserId(userId);
            List<Qna> qnaEntities = qnaService.read();
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
                                       String userId){
        try {
            Qna qna = QnaDto.toEntity(qnaDto);
            qna.setMember(memberService.getMember(userId));

            List<Qna> qnaEntities = qnaService.update(qna);
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
    public ResponseEntity<?> deleteQna( String userId,
                                       @RequestBody QnaDto qnaDto){
        try {
            Qna qna = QnaDto.toEntity(qnaDto);
            qna.setMember(memberService.getMember(userId));
            List<Qna> qnaEntities = qnaService.delete(qna);
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
