package com.jmt.controller;

import com.jmt.common.PagingUtil;
import com.jmt.dto.QnaDetailDto;
import com.jmt.dto.QnaDto;
import com.jmt.dto.ResponseDto;
import com.jmt.entity.Qna;
import com.jmt.service.MemberService;
import com.jmt.service.QnaService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
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
    public ResponseEntity<?> createQna(@RequestPart(value = "file", required = false) List<MultipartFile> multipartFiles,
                                       @RequestPart(value = "data") QnaDto qnaDto,
                                       @AuthenticationPrincipal String userid){

        qnaService.createQna(multipartFiles, qnaDto, userid);
        return ResponseEntity.ok().body("success");
    }

    @GetMapping
    public ResponseEntity<?> getQnaList(@RequestParam(defaultValue = "1") int page,
                                                         @RequestParam(defaultValue = "10") int size) {
        PagingUtil<QnaDto> qnaPaging = qnaService.getQnaList(page, size);

        return ResponseEntity.ok().body(qnaPaging);
    }

    @PostMapping("/admin/{qnaNum}")
    public ResponseEntity<?> updateQna(@RequestPart(value = "file", required = false) List<MultipartFile> multipartFiles,
                                       @RequestPart(value = "data") QnaDto qnaDto,
                                      @PathVariable Long qnaNum
                                      ,@AuthenticationPrincipal String userId){
        try {
            Qna update = qnaService.update(qnaNum, qnaDto, multipartFiles, userId);
            QnaDto updateDto = new QnaDto(update);
            return ResponseEntity.ok().body(updateDto);
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
            List<QnaDetailDto> qnaDetailDtos = qnaService.readAndViewCount(id);
            return ResponseEntity.ok().body(qnaDetailDtos);
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
            Qna qna = qnaService.readByQnaNum(id);
            QnaDto qnaDto = new QnaDto(qna);
            return ResponseEntity.ok().body(qnaDto);
        } catch (Exception e) {
            String error = e.getMessage();
            ResponseDto<QnaDto> responseDto = ResponseDto.<QnaDto>builder()
                    .error(error)
                    .build();
            return ResponseEntity.badRequest().body(responseDto);
        }
    }

    //첨부파일 확인용
    @PostMapping("/viewFile")
    public ResponseEntity<Resource> showFileImage(@RequestBody QnaDetailDto qnaDetailDto) throws IOException {
        Path path = Paths.get(qnaDetailDto.getServerPath());

        String contentType = Files.probeContentType(path);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentDisposition(ContentDisposition
                .builder("inline").filename(qnaDetailDto.getOriginalName(), StandardCharsets.UTF_8).build());

        headers.add(HttpHeaders.CONTENT_TYPE, contentType);

        Resource resource = new InputStreamResource(Files.newInputStream(path));

        return new ResponseEntity<>(resource, headers, HttpStatus.OK);
    }

    //제목 및 내용으로 검색해서 찾기
    @GetMapping("/search")
    public ResponseEntity<?> search(@RequestParam(value = "select") String select,
                                    @RequestParam(value = "result") String result){

        List<QnaDto> qnaDtoList = qnaService.searchDto(select, result);
        try {
            ResponseDto<QnaDto> responseDto = ResponseDto.<QnaDto>builder()
                    .data(qnaDtoList)
                    .build();
            return ResponseEntity.ok().body(responseDto);
        }catch (Exception e){
            String error = e.getMessage();
            ResponseDto<String> responseDto = ResponseDto.<String>builder()
                    .error(error)
                    .build();
            return ResponseEntity.ok().body(responseDto);
        }

    }
}
