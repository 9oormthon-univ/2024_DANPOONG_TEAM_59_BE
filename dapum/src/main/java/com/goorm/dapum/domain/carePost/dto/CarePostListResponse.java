package com.goorm.dapum.domain.carePost.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public record CarePostListResponse(
        Long carePostId,
        Long memberId,
        String nickname,
        String kakaoProfileImageUrl,
        String title,
        LocalDate careDate,
        String content,
        List<String> imageUrls,
        List<String> tags,
        LocalDateTime updatedAt,
        Long likeCount,
        Long commentCount,
        boolean isLiked
) {
}
