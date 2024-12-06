package com.goorm.dapum.domain.carePost.dto;

import com.goorm.dapum.domain.careComment.dto.CareCommentResponse;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

public record CarePostResponse(
        Long carePostId,
        Long memberId,
        String nickname,
        String profileImageUrl,
        String title,
        String content,
        List<String> imageUrls,
        String tag,
        boolean isEmergency,
        LocalDate careDate,
        LocalTime startTime,
        LocalTime endTime,
        LocalDateTime updatedAt,
        List<CareCommentResponse> commentResponse,
        Long likeCount,
        boolean isLike
) {
}
