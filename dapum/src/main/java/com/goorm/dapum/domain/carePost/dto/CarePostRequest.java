package com.goorm.dapum.domain.carePost.dto;

import com.goorm.dapum.domain.carePost.entity.CarePostTag;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public record CarePostRequest(
        String title,
        String content,
        List<String> imageUrls,
        CarePostTag carePostTag, // 태그 추가
        boolean isEmergency, // 긴급 돌봄 여부 추가
        LocalDate careDate,
        LocalTime startTime,
        LocalTime endTime
) {}