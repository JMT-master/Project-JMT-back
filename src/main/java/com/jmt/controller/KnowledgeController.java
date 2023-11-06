package com.jmt.controller;

import com.jmt.dto.*;
import com.jmt.service.KnowledgeService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityNotFoundException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Controller
@RequestMapping("/")
@RequiredArgsConstructor
public class KnowledgeController {
    private final KnowledgeService knowledgeService;


    // knowledge 첫 화면 List
    @GetMapping("knowledge")
    public ResponseEntity<List<KnowledgeDto>> readKnowledge() {

        List<KnowledgeDto> knowledgeDtos = knowledgeService.allKnowledgeList();

        return ResponseEntity.ok().body(knowledgeDtos);
    }

    // 지식in 전체/관광지/음식/숙박 category
    @GetMapping("knowledge/category")
    public ResponseEntity<ResponseDto> categoryReadKnowledge(@RequestParam(name = "name") String category) {
        List<KnowledgeDto> knowledgeDtos = knowledgeService.categoryKnowledgeList(category);

        if(knowledgeDtos == null) {
            return ResponseEntity.badRequest().body(ResponseDto.builder()
                                                                .error("error")
                                                                .build());
        } else {
            return ResponseEntity.ok().body(ResponseDto.<KnowledgeDto>builder()
                                                        .error("success")
                                                        .data(knowledgeDtos)
                                                        .build());
        }
    }

    // 제목, 내용별 검색 결과 반환
    // select : 제목/내용, searchResult : 입력한 검색어
    @GetMapping("knowledge/search")
    public ResponseEntity<ResponseDto> searchReadKnowledge(@RequestParam(value = "select") String select,
                                                           @RequestParam(value = "result") String result) {
        List<KnowledgeDto> search = knowledgeService.search(select, result);

        return ResponseEntity.ok().body(ResponseDto.<KnowledgeDto>builder()
                        .error("success")
                        .data(search)
                .build());
    }

    // 글 작성
    @PostMapping("knowledgeWrite/send")
    public ResponseEntity<?> createKnowledge(
            @AuthenticationPrincipal String userid,
//            @CookieValue(value = "ACCESS_TOKEN", required = false) String cookie,
            @RequestPart(value = "file", required = false) List<MultipartFile> multipartFiles,
            @RequestPart(value = "data")KnowledgeDto knowledgeDto
            ) {
        System.out.println("knowledgeDto = " + knowledgeDto);
        knowledgeService.create(multipartFiles,knowledgeDto,userid);

        return ResponseEntity.ok().body("success");
    }

    // 글 자세히 보기
    @PostMapping("knowledgeDetail")
    public ResponseEntity<List<KnowledgeSendDto>> createKnowledgeDetail(@RequestParam("id") Long id) {
        List<KnowledgeSendDto> knowledgeSendDtos = knowledgeService.detailForm(id);

        System.out.println("id = " + id);
        System.out.println("knowledgeSendDtos = " + knowledgeSendDtos);

        return ResponseEntity.ok().body(knowledgeSendDtos);
    }

    // 첨부파일
    @PostMapping("/knowledgeDetail/viewFile")
    public ResponseEntity<Resource> showFileImage(@RequestBody KnowledgeSendDto knowledgeSendDto) throws IOException {
        System.out.println("knowledgeSendDto = " + knowledgeSendDto);
        Path path = Paths.get(knowledgeSendDto.getServerPath());

        String contentType = Files.probeContentType(path);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentDisposition(
                ContentDisposition.builder("inline")
                .filename(knowledgeSendDto.getOriginalName(), StandardCharsets.UTF_8).build() // StandardCharsets.UTF_8 : UTF-8로 인코딩
        );

        headers.add(HttpHeaders.CONTENT_TYPE,contentType);

        Resource resource = new InputStreamResource(Files.newInputStream(path));

        return new ResponseEntity<>(resource,headers, HttpStatus.OK);
    }

    // 글 수정
    @PostMapping("knowledgeWrite/update")
    public ResponseEntity<?> updateKnowledge(
            @RequestBody KnowledgeUpdateDto knowledgeUpdateDto,
            @AuthenticationPrincipal String userid
    ) {
        try {
            knowledgeService.updateKnowledge(knowledgeUpdateDto,userid);
            return ResponseEntity.ok().body("success");
        } catch (Exception e) {
            e.getMessage();
            return ResponseEntity.ok().body("error");
        }


    }

    // 지식인 삭제
    @PostMapping("knowledgeDetail/delete")
    public ResponseEntity<ResponseDto> deleteKnowledgeDetail(@RequestBody KnowledgeSendDto knowledgeSendDto, @AuthenticationPrincipal String userid) {
        try {
            knowledgeService.deleteKnowledge(knowledgeSendDto,userid);
            return ResponseEntity.ok().body(ResponseDto.builder()
                            .error("success")
                    .build());
        } catch (Exception e) {
            return ResponseEntity.ok().body(ResponseDto.builder()
                    .error("error")
                    .build());
        }
    }


    // =========================================================
    // ========================== 답글 ==========================
    // =========================================================
    // 지식인 답글 리스트
    @GetMapping("/knowledgeDetail/answer")
    public ResponseEntity<List<KnowledgeAnswerDto>> readAnswer(@RequestParam(name = "num") Long num) {
        List<KnowledgeAnswerDto> answers = knowledgeService.readAnswer(num);

        return ResponseEntity.ok().body(answers);
    }

    // 지식인 답글 작성
    @PostMapping("/knowledgeDetail/answer/create")
    public ResponseEntity<ResponseDto<KnowledgeAnswerDto>> createAnswer(@RequestBody KnowledgeAnswerDto knowledgeAnswerDto,
                                                           @AuthenticationPrincipal String userid) {
        System.out.println("knowledgeAnswerDto = " + knowledgeAnswerDto);
        List<KnowledgeAnswerDto> answer = knowledgeService.createAnswer(knowledgeAnswerDto, userid);

        if(answer == null) {
            ResponseDto<KnowledgeAnswerDto> error = ResponseDto.<KnowledgeAnswerDto>builder().error("error").build();
            return ResponseEntity.badRequest().body(error);
        } else{
            ResponseDto<KnowledgeAnswerDto> success = ResponseDto.<KnowledgeAnswerDto>builder()
                    .error("success")
                    .data(answer)
                    .build();
            return ResponseEntity.ok().body(success);
        }

    }

    // 지식인 답글 좋아요 1 증가
    @PostMapping("/knowledgeDetail/answer/likeUp")
    public ResponseEntity<List<KnowledgeAnswerDto>> readAnswer(@RequestBody KnowledgeAnswerDto knowledgeAnswerDto) {
        List<KnowledgeAnswerDto> answer = knowledgeService.likeAddAnswer(knowledgeAnswerDto);

        return ResponseEntity.ok().body(answer);
    }

    // 지식인 답글 수정
    @PostMapping("/knowledgeDetail/answer/update")
    public ResponseEntity<ResponseDto> updateAnswer(@RequestBody KnowledgeAnswerDto knowledgeAnswerDto,
                                                    @AuthenticationPrincipal String userid) {
        try {
            List<KnowledgeAnswerDto> answer = knowledgeService.updateAnswer(knowledgeAnswerDto, userid);

            return ResponseEntity.ok().body(ResponseDto.<KnowledgeAnswerDto>builder()
                            .error("success")
                            .data(answer)
                    .build());
        } catch (Exception e) {
            return ResponseEntity.ok().body(ResponseDto.<KnowledgeAnswerDto>builder()
                    .error("error")
                    .build());
        }
    }

    // 지식인 답글 삭제
    @PostMapping("/knowledgeDetail/answer/delete")
    public ResponseEntity<ResponseDto> deleteAnswer(@RequestBody KnowledgeAnswerDto knowledgeAnswerDto,
                                                    @AuthenticationPrincipal String userid) {

        try {
            List<KnowledgeAnswerDto> answer = knowledgeService.deleteAnswer(knowledgeAnswerDto, userid);

            return ResponseEntity.ok().body(ResponseDto.<KnowledgeAnswerDto>builder()
                    .error("success")
                    .data(answer)
                    .build());
        } catch (EntityNotFoundException e) {
            e.getMessage();

            return ResponseEntity.badRequest().body(ResponseDto.builder()
                            .error("error")
                    .build());
        }
    }
}
