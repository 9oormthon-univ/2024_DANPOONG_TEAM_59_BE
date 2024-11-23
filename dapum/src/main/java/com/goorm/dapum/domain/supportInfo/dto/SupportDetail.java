package com.goorm.dapum.domain.supportInfo.dto;

import java.time.LocalDateTime;
import java.util.List;

public record SupportDetail(
        Long id,
        List<String> tags,
        String department,
        String title,
        String content,
        String url,
        LocalDateTime updatedAt
) {

}
