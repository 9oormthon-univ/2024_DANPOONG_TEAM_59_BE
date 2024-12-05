package com.goorm.dapum.domain.review.repository;

import com.goorm.dapum.domain.review.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    List<Review> findAllByToId(Long memberId);

    Optional<Review> findByCarePostIdAndFromId(Long carePostId, Long fromId);
    Optional<Review> findByCarePostIdAndToId(Long carePostId, Long toId);
}
