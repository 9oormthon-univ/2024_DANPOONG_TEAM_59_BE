package com.goorm.dapum.domain.PostReport.repository;

import com.goorm.dapum.domain.PostReport.entity.PostReport;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface PostReportRepository extends JpaRepository<PostReport, Long> {
    Optional<PostReport> findByPostIdAndMemberId(Long postId, Long memberId); // 중복 신고 방지
}

