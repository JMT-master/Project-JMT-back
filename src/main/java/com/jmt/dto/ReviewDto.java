package com.jmt.dto;

import com.jmt.entity.Review;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ReviewDto {
    private String reviewContent;
    private String reviewContentId;
    private Long reviewIdx;
    private String reviewImg;
    private int reviewLike;
    private LocalDateTime regDate;
    private LocalDateTime modDate;

    public static Review toEntity(ReviewDto dto){
        return Review.builder()
                .reviewContent(dto.getReviewContent())
                .reviewContentid(dto.getReviewContentId())
                .reviewIdx(dto.getReviewIdx())
                .reviewImage(dto.getReviewImg())
                .reviewLike(dto.reviewLike)
                .build();
    }
    public static ReviewDto toDto(Review entity){
        return ReviewDto.builder()
                .reviewContent(entity.getReviewContent())
                .reviewContentId(entity.getReviewContentid())
                .reviewIdx(entity.getReviewIdx())
                .reviewImg(entity.getReviewImage())
                .reviewLike(entity.getReviewLike())
                .build();

    }
}
