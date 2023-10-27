package com.jmt.controller;

import com.jmt.constant.Board;
import com.jmt.dto.KnowledgeDto;
import com.jmt.dto.KnowledgeSendDto;
import com.jmt.service.FileService;
import com.jmt.service.KnowledgeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Controller
@RequestMapping("/")
@RequiredArgsConstructor
public class KnowledgeController {
    private final KnowledgeService knowledgeService;
    private final FileService fileService;

    // knowledge 첫 화면 List
    @GetMapping("knowledge")
    public ResponseEntity<List<KnowledgeDto>> readKnowledge() {

        List<KnowledgeDto> knowledgeDtos = knowledgeService.allKnowledgeList();

        return ResponseEntity.ok().body(knowledgeDtos);
    }

    // 글 작성
    @PostMapping("knowledgeWrite/send")
    public ResponseEntity<?> createKnowledge(
            @AuthenticationPrincipal String userid,
//            @CookieValue(value = "ACCESS_TOKEN", required = false) String cookie,
            @RequestPart(value = "file", required = false) List<MultipartFile> multipartFiles,
            @RequestPart(value = "data")KnowledgeDto knowledgeDto
            ) {
        knowledgeService.create(multipartFiles,knowledgeDto,userid);

        return ResponseEntity.ok().body("success");
    }

    @PostMapping("knowledgeDetail")
    public ResponseEntity<List<KnowledgeSendDto>> createKnowledgeDetail(@RequestBody KnowledgeDto knowledgeDto, @RequestParam("id") Long id) {
        System.out.println("knowledgeDto = " + knowledgeDto);
        List<KnowledgeSendDto> knowledgeSendDtos = knowledgeService.writeNumKnowledgeList(knowledgeDto, id);

        System.out.println("id = " + id);
        System.out.println("knowledgeSendDtos = " + knowledgeSendDtos);

        return ResponseEntity.ok().body(knowledgeSendDtos);
    }

}
