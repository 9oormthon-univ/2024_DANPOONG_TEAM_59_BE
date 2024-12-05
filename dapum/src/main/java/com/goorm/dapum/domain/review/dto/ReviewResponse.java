package com.goorm.dapum.domain.review.dto;

import lombok.Builder;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Builder
public record ReviewResponse(
        Long reviewId,
        String fromNickname,
        String toNickname,
        LocalDateTime createdAt,
        String title,
        List<String> tags,
        LocalDate careDate,
        LocalTime startTime,
        LocalTime endTime,
        String content
) {
}
