package com.jmt.controller;

import com.jmt.dto.ReviewDto;
import com.jmt.entity.Review;
import com.jmt.service.ReviewService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.persistence.PreUpdate;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/review")
@RequiredArgsConstructor
@Slf4j
public class ReviewController {
    private final ReviewService reviewService;

    @PostMapping("/read")
    public List<ReviewDto> readAllReview(@RequestBody ReviewDto dto) {

        List<ReviewDto> reviewDtos = reviewService.readAll(dto.getReviewContentId());
        reviewDtos.forEach(review -> {
            log.debug("review : " + review);
        });
        return reviewDtos;
    }

    @GetMapping("/{cid}/{idx}")
    public Review readReview(
            @PathVariable(value = "cid") String cid,
            @PathVariable(value = "idx") Long idx
    ) {
        return reviewService.readReview(idx);
    }

    @PostMapping
    public ResponseEntity<ReviewDto> writeReview(@AuthenticationPrincipal String email, @RequestBody ReviewDto dto) {
        Review review = reviewService.writeReview(email,dto);

        ReviewDto reviewDto = ReviewDto.toDto(review);
        return ResponseEntity.ok().body(reviewDto);
    }

    @PutMapping
    public ResponseEntity<List<ReviewDto>> updateReview(@RequestBody ReviewDto dto) {
        Review review = reviewService.updateReview(dto);
        List<ReviewDto> reviewDtos = reviewService.readAll(dto.getReviewContentId());
                ReviewDto.toDto(review);
        return ResponseEntity.ok().body(reviewDtos);
    }

    @DeleteMapping
    public ResponseEntity<ReviewDto> deleteReveiw(@RequestBody ReviewDto dto) {
        Review review = reviewService.deleteReview(dto);
        ReviewDto reviewDto = ReviewDto.toDto(review);
        return ResponseEntity.ok().body(reviewDto);
    }
}
