package com.goorm.dapum.domain.carePostReport.entity;

public enum CareReportState {
    PENDING("신고"),  // 신고가 접수된 상태
    PROCESSED("신고처리됨"), // 신고가 처리된 상태
    REJECTED("신고반려");

    private final String description;

    // 생성자
    CareReportState(String description) {
        this.description = description;
    }

    // 상태 설명을 반환하는 메서드
    public String getDescription() {
        return description;
    }
}
