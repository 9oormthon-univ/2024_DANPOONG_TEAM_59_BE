package com.goorm.dapum.domain.review.repository;

import com.goorm.dapum.domain.review.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    List<Review> findAllByToId(Long memberId);
}
