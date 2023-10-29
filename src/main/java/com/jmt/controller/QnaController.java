package com.jmt.controller;

import com.jmt.common.PagingUtil;
import com.jmt.dto.QnaDto;
import com.jmt.dto.ResponseDto;
import com.jmt.entity.Qna;
import com.jmt.service.MemberService;
import com.jmt.service.QnaService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@Slf4j
@RequestMapping("/qna")
public class QnaController {

    @Autowired
    private QnaService qnaService;
    @Autowired
    private MemberService memberService;

    @PostMapping("/admin/write")
    public ResponseEntity<?> createQna(@RequestBody QnaDto qnaDto,
                                       @AuthenticationPrincipal String userid){
        try {
            Qna qna = QnaDto.toEntity(qnaDto);
            log.info("userId : " +userid);
//            qna.setQnaColNum(null);
            qna.updateModDate();
            qna.setMember(memberService.getMember(userid));
            log.info("qna.getmember : {}", qna.getMember());
            List<Qna> qnaEntities = qnaService.create(qna);
//            log.info("qnaEntities : {}",qnaEntities);
            List<QnaDto> qnaDtos = qnaEntities.stream().map(QnaDto::new)
                    .collect(Collectors.toList());
            log.info("qnaDtos : {}",qnaDtos);
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
    public ResponseEntity<?> getQnaList(@RequestParam(defaultValue = "1") int page,
                                                         @RequestParam(defaultValue = "10") int size) {
        PagingUtil<QnaDto> qnaPaging = qnaService.getQnaList(page, size);

        return ResponseEntity.ok().body(qnaPaging);
    }

//    @GetMapping
//    public ResponseEntity<?> readQna(String userId){
//        try {
//            List<Qna> qnaEntities = qnaService.read();
//            List<QnaDto> qnaDtos = qnaEntities.stream().map(QnaDto::new)
//                    .collect(Collectors.toList());
//            ResponseDto<QnaDto> responseDto = ResponseDto.<QnaDto>builder()
//                    .data(qnaDtos)
//                    .build();
//            return ResponseEntity.ok().body(responseDto);
//        }catch (Exception e){
//            String error = e.getMessage();
//            System.out.println("error = " + error);
//            ResponseDto<QnaDto> responseDto = ResponseDto.<QnaDto>builder()
//                    .error(error)
//                    .build();
//            return ResponseEntity.badRequest().body(responseDto);
//        }
//    }

    @PostMapping("/admin/{qnaNum}")
    public ResponseEntity<?> updateQna(@RequestBody QnaDto qnaDto,
                                      @PathVariable Long qnaNum
                                      ,@AuthenticationPrincipal String userId){
        try {
            Qna qna = qnaService.readByQnaNum(qnaNum);
            qna.setQnaCategory(qnaDto.getQnaCategory());
            qna.setQnaTitle(qnaDto.getQnaTitle());
            qna.setQnaContent(qnaDto.getQnaContent());
            qna.setMember(memberService.getMember(userId));
            qna.updateModDate();
            log.info("qna : {} ",qna);
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

    @DeleteMapping("/admin")
    public ResponseEntity<?> deleteQna(@AuthenticationPrincipal String userId,
                                       @RequestBody Long qnaNum){
        try {
            Qna qna = qnaService.readByQnaNum(qnaNum);
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

    //특정 qna 읽어오는 mapping
    @GetMapping("/{id}")
    public ResponseEntity<?> readByQnaColNum(@PathVariable Long id) {
        try {
            // qnaColNum을 사용하여 데이터베이스에서 해당 Qna를 검색
            List<Qna> qnas = qnaService.readByQnaListColNum(id);
            log.info("qnas : {}", qnas);
            List<QnaDto> qnaDtos = qnas.stream().map(QnaDto::new)
                    .collect(Collectors.toList());
            log.info("qnaDtos {} : ",qnaDtos);
            ResponseDto<QnaDto> responseDto = ResponseDto.<QnaDto>builder()
                    .data(qnaDtos)
                    .build();
            return ResponseEntity.ok().body(responseDto);
        } catch (Exception e) {
            String error = e.getMessage();
            ResponseDto<QnaDto> responseDto = ResponseDto.<QnaDto>builder()
                    .error(error)
                    .build();
            return ResponseEntity.badRequest().body(responseDto);
        }
    }

    //업데이트를 위한 getMapping
    //특정 qna 읽어오는 mapping
    @GetMapping("/admin/{id}")
    public ResponseEntity<?> readForUpdate(@PathVariable Long id,
                                           @AuthenticationPrincipal String userid) {
        try {
            // qnaColNum을 사용하여 데이터베이스에서 해당 Qna를 검색
            List<Qna> qnas = qnaService.readByQnaListColNum(id);
            List<QnaDto> qnaDtos = qnas.stream().map(QnaDto::new)
                    .collect(Collectors.toList());
            ResponseDto<QnaDto> responseDto = ResponseDto.<QnaDto>builder()
                    .data(qnaDtos)
                    .build();
            return ResponseEntity.ok().body(responseDto);
        } catch (Exception e) {
            String error = e.getMessage();
            ResponseDto<QnaDto> responseDto = ResponseDto.<QnaDto>builder()
                    .error(error)
                    .build();
            return ResponseEntity.badRequest().body(responseDto);
        }
    }
}
