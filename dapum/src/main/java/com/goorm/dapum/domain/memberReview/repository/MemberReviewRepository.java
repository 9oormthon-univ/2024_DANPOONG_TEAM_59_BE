package com.goorm.dapum.domain.memberReview.repository;

import com.goorm.dapum.domain.memberReview.entity.MemberReview;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberReviewRepository extends JpaRepository<MemberReview, Long> {
}
