package com.goorm.dapum.domain.chatroom.dto;

import lombok.Builder;

@Builder
public record ChatRoomList(
        Long chatRoomId,
        String otherUserName,
        String otherUserNeighborhood,
        String lastMessage,
        int unreadMessageCount
) {
}
