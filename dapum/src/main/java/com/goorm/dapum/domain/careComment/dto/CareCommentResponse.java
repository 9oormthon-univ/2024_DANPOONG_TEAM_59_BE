package com.goorm.dapum.domain.careComment.dto;

import java.time.LocalDateTime;

public record CareCommentResponse(
        Long careCommentId,
        Long memberId,
        String nickname,
        String imageUrl,
        String content,
        LocalDateTime updatedAt
) {
}
