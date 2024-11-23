package com.goorm.dapum.domain.carePost.dto;

import java.util.List;

public record CarePostRequest(
        String title,
        String content,
        List<String> imageUrls,
        List<String> tags
) {
}
