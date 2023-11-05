package com.jmt.controller;

import com.jmt.dto.QnaDetailDto;
import com.jmt.dto.QnaDto;
import com.jmt.dto.ReviewDto;
import com.jmt.entity.Review;
import com.jmt.service.ReviewService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.*;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.PreUpdate;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/review")
@RequiredArgsConstructor
@Slf4j
public class ReviewController {
    private final ReviewService reviewService;

    @PostMapping("/userChk")
    public Boolean userChk(@AuthenticationPrincipal String email, @RequestBody ReviewDto dto){
        log.info("chkwriter : " + dto.getReviewWriter());
        return email.equalsIgnoreCase(dto.getReviewWriter());
    }

    @PostMapping("/read")
    public ResponseEntity<List<ReviewDto>> readAllReview(
            @RequestBody ReviewDto dto) {
        List<ReviewDto> reviewDtos = reviewService.readAll(dto.getReviewContentId());
        return ResponseEntity.ok().body(reviewDtos);
    }

    @GetMapping("/{cid}/{idx}")
    public Review readReview(
            @PathVariable(value = "cid") String cid,
            @PathVariable(value = "idx") Long idx
    ) {
        return reviewService.readReview(idx);
    }

    @PostMapping
    public ResponseEntity<List<ReviewDto>> writeReview(@RequestPart(value = "file", required = false) MultipartFile multipartFile,
                                                       @RequestPart(value = "data") ReviewDto dto,
                                                       @AuthenticationPrincipal String userid) {
        log.debug("multipartFile : " + multipartFile);
        log.debug("dto : " + dto);
        Review review = reviewService.writeReview(multipartFile, userid, dto);
        List<ReviewDto> reviewDtos = reviewService.readAll(dto.getReviewContentId());
        return ResponseEntity.ok().body(reviewDtos);
    }

    @PutMapping
    public ResponseEntity<List<ReviewDto>> updateReview(
            @RequestBody ReviewDto dto,
            @AuthenticationPrincipal String email) {
        reviewService.updateReview(dto);
        List<ReviewDto> reviewDtos = reviewService.readAll(dto.getReviewContentId());
        return ResponseEntity.ok().body(reviewDtos);

    }

    @DeleteMapping
    public ResponseEntity<ReviewDto> deleteReview(@RequestBody ReviewDto dto) {
        Review review = reviewService.deleteReview(dto);
        ReviewDto reviewDto = ReviewDto.toDto(review);
        return ResponseEntity.ok().body(reviewDto);
    }

    @PostMapping("/viewFile")
    public ResponseEntity<Resource> showFileImage(@RequestBody ReviewDto dto) throws IOException {
        if (dto.getReviewImg() == null) {
            // 이미지 경로가 null이면 빈 응답을 반환
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        Path path = Paths.get(dto.getReviewImg());

        if (!Files.exists(path) || Files.isDirectory(path)) {
            // 이미지 파일이 존재하지 않거나 디렉토리인 경우, 빈 응답을 반환
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        String contentType = Files.probeContentType(path);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentDisposition(ContentDisposition
                .builder("inline").filename(dto.getReviewImg(), StandardCharsets.UTF_8).build());

        headers.add(HttpHeaders.CONTENT_TYPE, contentType);

        Resource resource = new InputStreamResource(Files.newInputStream(path));

        return new ResponseEntity<>(resource, headers, HttpStatus.OK);
    }
}
