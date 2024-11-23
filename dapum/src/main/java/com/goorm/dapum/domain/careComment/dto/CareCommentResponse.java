package com.goorm.dapum.domain.careComment.dto;

import java.time.LocalDateTime;

public record CareCommentResponse(
        Long id,
        String content,
        Long member_id,
        String nickname,
        String profileImageUrl,
        LocalDateTime createdAt
) {
}
