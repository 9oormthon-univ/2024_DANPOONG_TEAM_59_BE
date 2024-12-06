package com.goorm.dapum.domain.admin.dto;

import java.time.LocalDateTime;
import java.util.List;

public record PostReportList(
        Long postReportId,
        Long postId,
        Long memberId,
        String nickname,
        String profileImageUrl,
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
