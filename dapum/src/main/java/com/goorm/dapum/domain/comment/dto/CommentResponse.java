package com.goorm.dapum.domain.comment.dto;

import java.time.LocalDateTime;

public record CommentResponse(
        Long commentId,
        Long memberId,
        String nickname,
        String imageUrl,
        String content,
        LocalDateTime updatedAt
) {
}
