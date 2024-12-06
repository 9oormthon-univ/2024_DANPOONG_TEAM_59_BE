package com.goorm.dapum.domain.carePostReport.dto;

public record CareReportRequest(
        Long carePostId,  // carePostId로 수정
        String reason     // 신고 사유
) {
}
