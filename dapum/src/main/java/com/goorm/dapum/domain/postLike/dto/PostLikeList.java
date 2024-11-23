package com.goorm.dapum.domain.postLike.dto;

import java.time.LocalDateTime;
import java.util.List;

public record PostLikeList(
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
