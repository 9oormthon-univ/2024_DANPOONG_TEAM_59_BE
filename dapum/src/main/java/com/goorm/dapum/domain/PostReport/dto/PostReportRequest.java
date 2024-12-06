package com.goorm.dapum.domain.PostReport.dto;

public record PostReportRequest(
        Long postId,
        String reason // 신고 사유
) {
}
