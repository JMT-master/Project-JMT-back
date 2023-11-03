package com.jmt.controller;

import com.jmt.dto.ReviewDto;
import com.jmt.entity.Review;
import com.jmt.service.ReviewService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/review")
@RequiredArgsConstructor
@Slf4j
public class ReviewController {
    ReviewService reviewService;

    @GetMapping
    public List<Review> readAllReview(String cid){
        return reviewService.readAll(cid);
    }

    @GetMapping("/{idx}")
    public Review readReview(@PathVariable Long idx){
        return reviewService.readReview(idx);
    }

    @PostMapping
    public ResponseEntity<ReviewDto> writeReview(@RequestBody ReviewDto dto){
        Review review = reviewService.writeReview(dto);
        ReviewDto reviewDto = ReviewDto.toDto(review);
        return ResponseEntity.ok().body(reviewDto);
    }

    @PutMapping
    public ResponseEntity<ReviewDto> updateReview(@RequestBody ReviewDto dto){
        Review review = reviewService.updateReview(dto);
        ReviewDto reviewDto = ReviewDto.toDto(review);
        return ResponseEntity.ok().body(reviewDto);
    }

    @DeleteMapping
    public ResponseEntity<ReviewDto> deleteReveiw(@RequestBody ReviewDto dto){
        Review review = reviewService.deleteReview(dto);
        ReviewDto reviewDto = ReviewDto.toDto(review);
        return ResponseEntity.ok().body(reviewDto);
    }
}
