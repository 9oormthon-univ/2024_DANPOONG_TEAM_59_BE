package com.goorm.dapum.domain.post.dto;

import java.time.LocalDateTime;
import java.util.List;

public record PostListResponse(
        Long postId,
        Long memberId,
        String nickname,
        String title,
        String content,
        List<String> imageUrls,
        List<String> tags,
        LocalDateTime updatedAt,
        Long likeCount,
        Long commentCount,
        boolean isLiked
) {
}
