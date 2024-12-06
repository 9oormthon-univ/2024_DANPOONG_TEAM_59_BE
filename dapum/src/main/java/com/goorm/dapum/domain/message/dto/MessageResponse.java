package com.goorm.dapum.domain.message.dto;

import com.goorm.dapum.domain.message.entity.Message;

import java.time.LocalDateTime;
import java.util.List;

public record MessageResponse(
        Long messageId,
        String senderNickname,
        String content,
        LocalDateTime createdAt,
        boolean isRead,
        List<String> imageUrls // 이미지 URL 리스트 추가
) {
    // Message 엔티티로부터 DTO 생성
    public MessageResponse(Message message) {
        this(
                message.getId(),
                message.getSender().getNickname(),
                message.getContent(),
                message.getCreatedAt(),
                message.isRead(),
                message.getImageUrls() // 이미지 URL 리스트 추가
        );
    }
}
