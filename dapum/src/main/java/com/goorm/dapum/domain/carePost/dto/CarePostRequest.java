package com.goorm.dapum.domain.carePost.dto;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public record CarePostRequest(
        String title,
        String content,
        List<String> imageUrls,
        List<String> tags,
        LocalDate careDate,
        LocalTime startTime,
        LocalTime endTime
) {}

