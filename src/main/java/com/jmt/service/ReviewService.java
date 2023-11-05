package com.jmt.service;

import com.jmt.dto.ReviewDto;
import com.jmt.entity.Review;
import com.jmt.repository.MemberRepository;
import com.jmt.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final MemberRepository memberRepository;

    @Transactional
    public List<ReviewDto> readAll(String cid) {
        List<ReviewDto> reviewDtoList = new ArrayList<>();
        reviewRepository.findAllByReviewContentidOrderByRegDateAsc(cid).forEach(review -> {
            reviewDtoList.add(ReviewDto.toDto(review));
        });
        return reviewDtoList;
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
    public Review writeReview(String email, ReviewDto dto){
        Review review = ReviewDto.toEntity(dto);
        long maxIdx = 0;
        if(reviewRepository.getReviewByMaxIdx().isPresent()){
            maxIdx = reviewRepository.getReviewByMaxIdx().get();
        }
        review.setReviewIdx(maxIdx+1);
        // 추후 확인
        review.setMember(memberRepository.findByEmailAndSocialYn(email,"N").get());
        reviewRepository.save(review);
        return review;
    }
}
