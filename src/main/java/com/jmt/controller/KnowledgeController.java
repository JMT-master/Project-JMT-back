package com.jmt.controller;

import com.jmt.constant.Board;
import com.jmt.dto.KnowledgeDto;
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

    @CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
    @PostMapping("knowledgeWrite/send")
    public ResponseEntity<?> createKnowledge(
            @AuthenticationPrincipal String userid,
//            @CookieValue(value = "ACCESS_TOKEN", required = false) String cookie,
            @RequestPart(value = "file", required = false) List<MultipartFile> multipartFiles,
            @RequestPart(value = "data")KnowledgeDto knowledgeDto
            ) {
        System.out.println("multipartFiles = " + multipartFiles);
        System.out.println("knowledgeDto = " + knowledgeDto);
        System.out.println("userid = " + userid);

        if(multipartFiles != null) {
            for(int i=0; i< multipartFiles.size(); i++) {
                String originalFilename = multipartFiles.get(i).getOriginalFilename();
                long size = multipartFiles.get(i).getSize();
                System.out.println("originalFilename = " + originalFilename);
                System.out.println("size = " + size);
            }

            List<String> strings = fileService.fileUpload(multipartFiles, Board.KN, 0);
            System.out.println("strings = " + strings);
        }

        return ResponseEntity.ok().body("success");
    }

}
