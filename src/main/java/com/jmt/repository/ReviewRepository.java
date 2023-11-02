package com.jmt.repository;

import com.jmt.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, String> {
    Review findByReviewIdx(Long idx);

    List<Review> findAllByReviewContentidOrderByRegDateDesc(String cid);
}
