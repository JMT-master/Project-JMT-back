package com.jmt.controller;

import com.jmt.dto.ReviewDto;
import com.jmt.entity.Review;
import com.jmt.service.ReviewService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
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
        log.debug("reviewDto : " + dto.getReviewContentId());
//        log.debug("review : " + reviewService.readAll(dto.getReviewContentId()));
        List<Review> reviews = reviewService.readAll(dto.getReviewContentId());
        List<ReviewDto> reviewDtos = new ArrayList<>();

        reviews.forEach(review -> {
            reviewDtos.add(ReviewDto.toDto(review));
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
    public ResponseEntity<ReviewDto> writeReview(@RequestBody ReviewDto dto) {
        Review review = reviewService.writeReview(dto);
        ReviewDto reviewDto = ReviewDto.toDto(review);
        return ResponseEntity.ok().body(reviewDto);
    }

    @PutMapping
    public ResponseEntity<ReviewDto> updateReview(@RequestBody ReviewDto dto) {
        Review review = reviewService.updateReview(dto);
        ReviewDto reviewDto = ReviewDto.toDto(review);
        return ResponseEntity.ok().body(reviewDto);
    }

    @DeleteMapping
    public ResponseEntity<ReviewDto> deleteReveiw(@RequestBody ReviewDto dto) {
        Review review = reviewService.deleteReview(dto);
        ReviewDto reviewDto = ReviewDto.toDto(review);
        return ResponseEntity.ok().body(reviewDto);
    }
}
