package com.goorm.dapum.domain.review.repository;

import com.goorm.dapum.domain.review.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ReviewRepository extends JpaRepository<Review, Long> {

    Optional<Review> findByChatRoomIdAndFromId(Long chatRoomId, Long id);

    Optional<Review> findByChatRoomIdAndToId(Long chatRoomId, Long id);
}
