package com.goorm.dapum.domain.post.dto;

import java.util.List;

public record PostResponse(
        Long postId,
        Long memberId,
        String title,
        String content,
        List<String> imageUrls,
        List<String> keywords
) {
}
