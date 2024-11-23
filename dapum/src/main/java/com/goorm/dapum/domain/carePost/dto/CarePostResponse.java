package com.goorm.dapum.domain.carePost.dto;

import com.goorm.dapum.domain.careComment.dto.CareCommentResponse;

import java.time.LocalDateTime;
import java.util.List;

public record CarePostResponse(
        Long carePostId,
        Long memberId,
        String nickname,
        String profileImageUrl,
        String title,
        String content,
        List<String> imageUrls,
        List<String> tags,
        LocalDateTime updatedAt,
        List<CareCommentResponse> commentResponse,
        Long likeCount,
        boolean isLike
) {
}
