package com.jmt.service;

import com.jmt.constant.Board;
import com.jmt.dto.ReviewDto;
import com.jmt.entity.Review;
import com.jmt.repository.MemberRepository;
import com.jmt.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class ReviewService {
    @Value("${itemImgLocation}")
    private String itemImageLocation;

    private final ReviewRepository reviewRepository;
    private final MemberRepository memberRepository;
    private final FileService fileService;


    @Transactional
    public List<ReviewDto> readAll(String cid) {
        List<ReviewDto> reviewDtoList = new ArrayList<>();
        reviewRepository.findAllByReviewContentidOrderByRegDateDesc(cid).forEach(review -> reviewDtoList.add(ReviewDto.toDto(review)));
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
        review.setModDate(LocalDateTime.now());
        return review;
    }

    @Transactional
    public Review deleteReview(@RequestBody ReviewDto dto) {
        Review review = reviewRepository.findByReviewIdx(dto.getReviewIdx());
        reviewRepository.delete(review);
        return review;
    }

    @Transactional
    public Review writeReview(@RequestPart(value = "file", required = false) MultipartFile file, String email, ReviewDto dto) {
        Review review = ReviewDto.toEntity(dto);
        long maxIdx = 0;
        if (reviewRepository.getReviewByMaxIdx().isPresent()) {
            maxIdx = reviewRepository.getReviewByMaxIdx().get();
        }
        maxIdx += 1;
        if (file != null) {
            String filePath = itemImageLocation + "/" + "Review_" + maxIdx + "_" + fileService.generateRandomString();
            File dest = new File(filePath);
            review.setReviewImage(filePath);
            try {
                file.transferTo(dest); // 파일 업로드 작업 수행
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        review.setReviewIdx(maxIdx);
        review.setMember(memberRepository.findByEmail(email).get());


        reviewRepository.save(review);

        return review;
    }



}
