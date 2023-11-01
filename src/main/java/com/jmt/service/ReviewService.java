package com.jmt.service;

import com.jmt.dto.ReviewDto;
import com.jmt.entity.Review;
import com.jmt.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;

    @Transactional
    public List<Review> readAll(String cid) {
        return reviewRepository.findAllByReviewContentidOrderByRegDateDesc(cid);
    }

    @Transactional
    public Review readReview(Long idx) {
        return reviewRepository.findByReviewIdx(idx);
    }

    @Transactional
    public Review updateReview(ReviewDto dto) {
        Review review = reviewRepository.findByReviewIdx(dto.getReviewIdx());
        review.setReviewImage(dto.getReviewImg());
        review.setReviewContent(dto.getReviewContent());
        return review;
    }

    @Transactional
    public Review deleteReview(ReviewDto dto){
        Review review = reviewRepository.findByReviewIdx(dto.getReviewIdx());
        reviewRepository.delete(review);
        return review;
    }

    @Transactional
    public Review writeReview(ReviewDto dto){
        Review review = ReviewDto.toEntity(dto);
        reviewRepository.save(review);
        return review;
    }
}
