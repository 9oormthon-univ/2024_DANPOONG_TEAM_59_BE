package com.goorm.dapum.domain.supportInfo.dto;

import java.time.LocalDateTime;
import java.util.List;

public record SupportInfoList(
        Long id,
        List<String> category,
        String title,
        String department,
        LocalDateTime updatedAt
) {
}
