package com.goorm.dapum.domain.PostReport.entity;

public enum ReportState {
    PENDING("신고접수"),  // 신고가 접수된 상태
    PROCESSED("신고처리"), // 신고가 처리된 상태
    REJECTED("신고반려");

    private final String description;

    // 생성자
    ReportState(String description) {
        this.description = description;
    }

    // 상태 설명을 반환하는 메서드
    public String getDescription() {
        return description;
    }
}

