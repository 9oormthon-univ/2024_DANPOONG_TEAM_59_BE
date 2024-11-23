package com.goorm.dapum.domain.post.dto;

import com.goorm.dapum.domain.comment.dto.CommentResponse;

import java.time.LocalDateTime;
import java.util.List;

public record PostResponse(
        Long postId,
        Long memberId,
        String nickname,
        String profileImageUrl,
        String title,
        String content,
        List<String> imageUrls,
        List<String> tags,
        LocalDateTime updatedAt,
        List<CommentResponse> commentResponse,
        Long likeCount,
        boolean isLike
) {
}
