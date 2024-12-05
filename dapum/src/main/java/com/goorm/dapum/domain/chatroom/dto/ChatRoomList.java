package com.goorm.dapum.domain.chatroom.dto;

import lombok.Builder;

@Builder
public record ChatRoomList(
        Long chatRoomId,
        String otherUserName,
        String otherUserNeighborhood,
        String profileImage,
        String lastMessage,
        int unreadMessageCount
) {
}
