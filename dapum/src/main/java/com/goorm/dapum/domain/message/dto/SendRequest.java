package com.goorm.dapum.domain.message.dto;

public record SendRequest(
        Long receiverId,
        String content
) {
}
