package com.goorm.dapum.domain.CarePostReport.repository;

import com.goorm.dapum.domain.CarePostReport.entity.CareReport;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CareReportRepository extends JpaRepository<CareReport, Long> {
    Optional<CareReport> findByCarePostIdAndMemberId(Long carePostId, Long memberId); // 중복 신고 방지
}
