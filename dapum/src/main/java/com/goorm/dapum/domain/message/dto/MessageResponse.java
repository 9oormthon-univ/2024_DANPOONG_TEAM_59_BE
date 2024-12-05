package com.goorm.dapum.domain.message.dto;

import com.goorm.dapum.domain.message.entity.Message;

import java.time.LocalDateTime;

public record MessageResponse(
        Long messageId,
        String senderNickname,
        String content,
        LocalDateTime createdAt,
        boolean isRead
) {
    public MessageResponse(Message message) {
        this(
                message.getId(),
                message.getSender().getNickname(),
                message.getContent(),
                message.getCreatedAt(),
                message.isRead()
        );
    }
}

