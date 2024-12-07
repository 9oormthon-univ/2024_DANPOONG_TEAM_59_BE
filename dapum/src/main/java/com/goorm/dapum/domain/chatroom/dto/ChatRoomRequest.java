package com.goorm.dapum.domain.chatroom.dto;

public record ChatRoomRequest(
        String tag, // 돌봄, 나눔
        Long id // 게시글 id
) {
}
