package com.goorm.dapum.domain.review.dto;

import lombok.Builder;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Builder
public record ReviewResponse(
        Long reviewId,
        String fromNickname,
        String toNickname,
        LocalDateTime createdAt,
        String title,
        String tag,
        boolean isEmergency,
        LocalDate careDate,
        LocalTime startTime,
        LocalTime endTime,
        String content
) {
}
