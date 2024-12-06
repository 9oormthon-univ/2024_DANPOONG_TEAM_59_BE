package com.goorm.dapum.domain.review.dto;

public record ReviewRequest(
        Long chatRoomId,
        Long toMemberId,
        int rating,
        String content
) {
}
