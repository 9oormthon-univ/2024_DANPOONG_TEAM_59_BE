package com.goorm.dapum.domain.chatroom.dto;

import com.goorm.dapum.domain.chatroom.entity.ChatRoom;
import com.goorm.dapum.domain.message.dto.MessageResponse;

import java.util.List;

public record ChatRoomResponse(
        Long chatRoomId,
        String member1Nickname,
        String member2Nickname,
        List<MessageResponse> messages
) {
    public ChatRoomResponse(ChatRoom chatRoom, List<MessageResponse> messages) {
        this(
                chatRoom.getId(),
                chatRoom.getMember1().getNickname(),
                chatRoom.getMember2().getNickname(),
                messages
        );
    }
}
