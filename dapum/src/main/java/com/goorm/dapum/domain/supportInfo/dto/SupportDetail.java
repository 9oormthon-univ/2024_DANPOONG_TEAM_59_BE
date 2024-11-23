package com.goorm.dapum.domain.supportInfo.dto;

import com.goorm.dapum.domain.supportInfo.entity.SupportInfo;

import java.time.LocalDateTime;
import java.util.List;

public record SupportDetail(
        Long id,
        List<String> category,
        String department,
        String title,
        String content,
        String url,
        LocalDateTime updatedAt
) {

}
