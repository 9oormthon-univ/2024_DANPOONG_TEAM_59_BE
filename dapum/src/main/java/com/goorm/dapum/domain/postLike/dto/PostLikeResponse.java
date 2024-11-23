package com.goorm.dapum.domain.postLike.dto;

public record PostLikeResponse(
        Long likeCount,
        boolean isLiked
) {
}
