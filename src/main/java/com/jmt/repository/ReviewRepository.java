package com.jmt.repository;

import com.jmt.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

public interface ReviewRepository extends JpaRepository<Review, String> {
    Review findByReviewIdx(Long idx);

    List<Review> findAllByReviewContentidOrderByRegDateDesc(String cid);

    @Query("select max(reviewIdx) from Review")
    Optional<Long> getReviewByMaxIdx();
}
