package com.goorm.dapum.domain.post.dto;

import java.time.LocalDateTime;
import java.util.List;

public record PostListResponse(
        Long postId,
        Long memberId,
        String title,
        String content,
        List<String> imageUrls,
        List<String> keywords,
        LocalDateTime updateddAt
) {
}
