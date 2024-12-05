package com.goorm.dapum.domain.review.dto;

public record ReviewRequest(
        Long carePostId,
        Long toMemberId,
        int rating,
        String content
) {
}
