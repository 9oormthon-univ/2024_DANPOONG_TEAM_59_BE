package com.goorm.dapum.domain.careComment.dto;

public record CareCommentRequest(
        Long careRequestId,
        String content
) {
}
