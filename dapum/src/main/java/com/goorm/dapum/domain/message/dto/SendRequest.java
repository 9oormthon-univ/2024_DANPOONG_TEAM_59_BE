package com.goorm.dapum.domain.message.dto;

import java.util.List;

public record SendRequest(
        Long receiverId,
        String content,
        List<String> imageUrls
) {
}
