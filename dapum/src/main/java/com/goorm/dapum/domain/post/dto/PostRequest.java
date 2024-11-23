package com.goorm.dapum.domain.post.dto;

import java.util.List;

public record PostRequest(
        String title,
        String content,
        List<String> imageUrls,
        List<String> keywords
) {
}
